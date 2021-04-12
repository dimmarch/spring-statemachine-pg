package dev.marchuk.statemachine.config.state;

import dev.marchuk.statemachine.domain.ActivityState;
import dev.marchuk.statemachine.domain.Event;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.stereotype.Component;

@Component
public class PublishedStateTransitionConfigurer implements StateTransitionConfigurer {
    @Override
    public ActivityState getState() {
        return ActivityState.PUBLISHED;
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
