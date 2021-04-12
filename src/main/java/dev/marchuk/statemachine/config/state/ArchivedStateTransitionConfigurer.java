package dev.marchuk.statemachine.config.state;

import dev.marchuk.statemachine.config.StateTransitionPermissions;
import dev.marchuk.statemachine.domain.ActivityState;
import dev.marchuk.statemachine.domain.Event;
import dev.marchuk.statemachine.domain.Role;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArchivedStateTransitionConfigurer implements StateTransitionConfigurer {
    @Override
    public ActivityState getState() {
        return ActivityState.ARCHIVED;
    }

    @Override
    public StateTransitionPermissions getPermissions() {
        return new StateTransitionPermissions()
                .add(Role.ADMIN, List.of(Event.RESTORE));
    }

    @Override
    public void configureTransitions(StateMachineTransitionConfigurer<ActivityState, Event> transitions) throws Exception {
        transitions.withExternal()
                .source(ActivityState.ARCHIVED)
                .target(ActivityState.EDITED)
                .event(Event.RESTORE);
    }
}
