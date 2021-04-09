package dev.marchuk.statemachine.service;

import dev.marchuk.statemachine.domain.ActivityState;
import dev.marchuk.statemachine.domain.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityTransitionService {

    private final StateMachine<ActivityState, Event> stateMachine;

    public String makeTransition(Event transition) {
        var message = MessageBuilder
                .withPayload(transition).build();
        var currentState = stateMachine.getState();
        var results = stateMachine.sendEvent(Mono.just(message))
                .collectList().block();
        var isSucceeded = results.stream()
                .map(StateMachineEventResult::getResultType)
                .noneMatch(StateMachineEventResult.ResultType.DENIED::equals);
        if (!isSucceeded) {
            throw new UnsupportedOperationException(transition.name() + " event is denied");
        }
        var newState = stateMachine.getState();
        return String.format("%s -> %s", currentState.getId().name(), newState.getId().name());
    }

    public void setState(StateMachine<ActivityState, Event> stateMachine, ActivityState state) {
        stateMachine
                .getStateMachineAccessor()
                .doWithAllRegions(access -> {
                    access.resetStateMachine(new DefaultStateMachineContext<>(state, null, null, null, null));
                });
        stateMachine.startReactively().subscribe();
    }

}
