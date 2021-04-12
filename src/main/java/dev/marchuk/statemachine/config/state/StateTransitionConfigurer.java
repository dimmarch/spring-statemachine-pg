package dev.marchuk.statemachine.config.state;

import dev.marchuk.statemachine.domain.ActivityState;
import dev.marchuk.statemachine.domain.Event;
import dev.marchuk.statemachine.domain.Role;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.List;
import java.util.Map;

public interface StateTransitionConfigurer {
    void configureTransitions(StateMachineTransitionConfigurer<ActivityState, Event> transitions) throws Exception;

    ActivityState getState();

    Map<Role, List<Event>> getTransitionsPermissionMap();
}
