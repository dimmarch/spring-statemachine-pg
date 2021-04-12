package dev.marchuk.statemachine.config.state;

import dev.marchuk.statemachine.domain.ActivityState;
import dev.marchuk.statemachine.domain.Event;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

public interface StateTransitionConfigurer {
    void configureTransitions(StateMachineTransitionConfigurer<ActivityState, Event> transitions) throws Exception;

    ActivityState getState();
}
