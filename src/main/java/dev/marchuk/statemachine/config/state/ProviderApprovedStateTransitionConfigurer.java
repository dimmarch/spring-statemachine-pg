package dev.marchuk.statemachine.config.state;

import dev.marchuk.statemachine.domain.ActivityState;
import dev.marchuk.statemachine.domain.Event;
import dev.marchuk.statemachine.domain.Role;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ProviderApprovedStateTransitionConfigurer implements StateTransitionConfigurer {
    @Override
    public ActivityState getState() {
        return ActivityState.PROVIDER_APPROVED;
    }

    @Override
    public Map<Role, List<Event>> getTransitionsPermissionMap() {
        return Map.of(Role.ADMIN, List.of(Event.PUBLISH));
    }

    @Override
    public void configureTransitions(StateMachineTransitionConfigurer<ActivityState, Event> transitions) throws Exception {
        transitions.withExternal()
                .source(ActivityState.PROVIDER_APPROVED)
                .target(ActivityState.PUBLISHED)
                .event(Event.PUBLISH);
    }
}
