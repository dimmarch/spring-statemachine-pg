package dev.marchuk.statemachine.config.state;

import dev.marchuk.statemachine.config.StateTransitionPermissions;
import dev.marchuk.statemachine.domain.ActivityState;
import dev.marchuk.statemachine.domain.Event;
import dev.marchuk.statemachine.domain.Role;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PublishedStateTransitionConfigurer implements StateTransitionConfigurer {
    @Override
    public ActivityState getState() {
        return ActivityState.PUBLISHED;
    }

    @Override
    public StateTransitionPermissions getPermissions() {
        return new StateTransitionPermissions()
                .add(Role.ADMIN, List.of(Event.ARCHIVE, Event.EDIT));
    }

    @Override
    public void configureTransitions(StateMachineTransitionConfigurer<ActivityState, Event> transitions) throws Exception {
        transitions.withExternal()
                .source(ActivityState.PUBLISHED)
                .target(ActivityState.ARCHIVED)
                .event(Event.ARCHIVE)
                .and()

                .withExternal()
                .source(ActivityState.PUBLISHED)
                .target(ActivityState.EDITED)
                .event(Event.EDIT);
    }
}
