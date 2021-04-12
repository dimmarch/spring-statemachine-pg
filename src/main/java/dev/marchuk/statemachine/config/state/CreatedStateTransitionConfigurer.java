package dev.marchuk.statemachine.config.state;

import dev.marchuk.statemachine.config.StateTransitionPermissions;
import dev.marchuk.statemachine.domain.ActivityState;
import dev.marchuk.statemachine.domain.Event;
import dev.marchuk.statemachine.domain.Role;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class CreatedStateTransitionConfigurer implements StateTransitionConfigurer {
    @Override
    public ActivityState getState() {
        return ActivityState.CREATED;
    }

    @Override
    public StateTransitionPermissions getPermissions() {
        return new StateTransitionPermissions()
                .add(Role.ADMIN, List.of(Event.EDIT, Event.DELETE, Event.SEND_FOR_APPROVE, Event.PUBLISH));
    }

    @Override
    public void configureTransitions(StateMachineTransitionConfigurer<ActivityState, Event> transitions) throws Exception {
        transitions.withExternal()
                .source(ActivityState.CREATED)
                .target(ActivityState.EDITED)
                .event(Event.EDIT)
                .and()

                .withExternal()
                .source(ActivityState.CREATED)
                .target(ActivityState.DELETED)
                .event(Event.DELETE)
                .and()

                .withExternal()
                .source(ActivityState.CREATED)
                .target(ActivityState.WAIT_FOR_PROVIDER_APPROVE)
                .event(Event.SEND_FOR_APPROVE)
                .and()

                .withExternal()
                .source(ActivityState.CREATED)
                .target(ActivityState.PUBLISHED)
                .event(Event.PUBLISH);
    }
}
