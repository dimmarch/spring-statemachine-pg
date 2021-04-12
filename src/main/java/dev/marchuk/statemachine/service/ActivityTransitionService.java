package dev.marchuk.statemachine.service;

import dev.marchuk.statemachine.dao.ActivityRepository;
import dev.marchuk.statemachine.domain.Activity;
import dev.marchuk.statemachine.domain.ActivityState;
import dev.marchuk.statemachine.domain.Event;
import dev.marchuk.statemachine.domain.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityTransitionService {

    private final StateMachineFactory<ActivityState, Event> stateMachineFactory;
    private final TransitionPermissionChecker transitionPermissionChecker;
    private final ActivityRepository activityRepository;

    public String makeTransition(Role role, Integer activityId, Event transition) {
        var activity = activityRepository.getActivity(activityId);
        var stateMachine = getStateMachine(activity);
        var message = MessageBuilder
                .withPayload(transition).build();
        var currentState = stateMachine.getState();
        if (!transitionPermissionChecker.isPermitted(role, currentState.getId(), transition)) {
            throw new UnsupportedOperationException(transition.name() + " event is not permitted");
        }
        var results = stateMachine.sendEvent(Mono.just(message))
                .collectList().block();
        var isSucceeded = results.stream()
                .map(StateMachineEventResult::getResultType)
                .noneMatch(StateMachineEventResult.ResultType.DENIED::equals);
        if (!isSucceeded) {
            throw new UnsupportedOperationException(transition.name() + " event is denied");
        }
        var newState = stateMachine.getState();
        activity.setState(newState.getId());
        activityRepository.saveActivity(activity);
        return String.format("%s -> %s", currentState.getId().name(), newState.getId().name());
    }

    public StateMachine<ActivityState, Event> getStateMachine(Activity activity) {
        var stateMachine = stateMachineFactory.getStateMachine();
        stateMachine
                .getStateMachineAccessor()
                .doWithAllRegions(access -> {
                    access.resetStateMachine(new DefaultStateMachineContext<>(activity.getState(), null, null, null, null));
                });
        stateMachine.getExtendedState().getVariables().put("ACTIVITY_ID", activity.getId());
        stateMachine.startReactively().subscribe();
        return stateMachine;
    }

}
