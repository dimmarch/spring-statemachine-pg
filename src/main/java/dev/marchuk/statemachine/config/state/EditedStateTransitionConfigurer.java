package dev.marchuk.statemachine.config.state;

import dev.marchuk.statemachine.config.StateTransitionPermissions;
import dev.marchuk.statemachine.domain.ActivityState;
import dev.marchuk.statemachine.domain.Event;
import dev.marchuk.statemachine.domain.Role;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EditedStateTransitionConfigurer implements StateTransitionConfigurer {
    @Override
    public ActivityState getState() {
        return ActivityState.EDITED;
    }

    @Override
    public StateTransitionPermissions getPermissions() {
        return new StateTransitionPermissions()
                .add(Role.ADMIN, List.of(Event.SEND_FOR_APPROVE, Event.EDIT, Event.PUBLISH));
    }

    @Override
    public void configureTransitions(StateMachineTransitionConfigurer<ActivityState, Event> transitions) throws Exception {
        transitions.withExternal()
                .source(ActivityState.EDITED)
                .target(ActivityState.WAIT_FOR_PROVIDER_APPROVE)
                .event(Event.SEND_FOR_APPROVE)
                .and()

                .withInternal()
                .source(ActivityState.EDITED)
                .event(Event.EDIT)
                .and()

                .withExternal()
                .source(ActivityState.EDITED)
                .target(ActivityState.PUBLISHED)
                .event(Event.PUBLISH);
    }
}
