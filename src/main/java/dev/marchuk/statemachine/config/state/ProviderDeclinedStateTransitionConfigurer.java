package dev.marchuk.statemachine.config.state;

import dev.marchuk.statemachine.domain.ActivityState;
import dev.marchuk.statemachine.domain.Event;
import dev.marchuk.statemachine.domain.Role;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ProviderDeclinedStateTransitionConfigurer implements StateTransitionConfigurer {
    @Override
    public ActivityState getState() {
        return ActivityState.PROVIDER_DECLINED;
    }

    @Override
    public Map<Role, List<Event>> getTransitionsPermissionMap() {
        return Map.of(Role.ADMIN, List.of(Event.EDIT));
    }

    @Override
    public void configureTransitions(StateMachineTransitionConfigurer<ActivityState, Event> transitions) throws Exception {
        transitions.withExternal()
                .source(ActivityState.PROVIDER_DECLINED)
                .target(ActivityState.EDITED)
                .event(Event.EDIT);
    }
}
