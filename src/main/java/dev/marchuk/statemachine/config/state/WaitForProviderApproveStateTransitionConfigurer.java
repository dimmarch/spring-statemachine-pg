package dev.marchuk.statemachine.config.state;

import dev.marchuk.statemachine.config.StateTransitionPermissions;
import dev.marchuk.statemachine.domain.ActivityState;
import dev.marchuk.statemachine.domain.Event;
import dev.marchuk.statemachine.domain.Role;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WaitForProviderApproveStateTransitionConfigurer implements StateTransitionConfigurer {
    @Override
    public ActivityState getState() {
        return ActivityState.WAIT_FOR_PROVIDER_APPROVE;
    }

    @Override
    public StateTransitionPermissions getPermissions() {
        return new StateTransitionPermissions()
                .add(Role.PROVIDER, List.of(Event.APPROVE, Event.REJECT));
    }

    @Override
    public void configureTransitions(StateMachineTransitionConfigurer<ActivityState, Event> transitions) throws Exception {
        transitions.withExternal()
                .source(ActivityState.WAIT_FOR_PROVIDER_APPROVE)
                .target(ActivityState.PROVIDER_APPROVED)
                .event(Event.APPROVE)
                .and()

                .withExternal()
                .source(ActivityState.WAIT_FOR_PROVIDER_APPROVE)
                .target(ActivityState.PROVIDER_DECLINED)
                .event(Event.REJECT);
    }
}
