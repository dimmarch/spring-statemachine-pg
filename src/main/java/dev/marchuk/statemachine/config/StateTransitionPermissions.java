package dev.marchuk.statemachine.config;

import dev.marchuk.statemachine.domain.Event;
import dev.marchuk.statemachine.domain.Role;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


public class StateTransitionPermissions {
    private final Map<Role, Set<Event>> permissionMap;

    public StateTransitionPermissions() {
        permissionMap = new HashMap<>();
    }

    public StateTransitionPermissions add(Role role, List<Event> events) {
        var permissions = permissionMap.getOrDefault(role, new HashSet<>());
        permissions.addAll(events);
        permissionMap.put(role, permissions);
        return this;
    }

    public boolean isPermitted(Role role, Event event) {
        return Optional.ofNullable(permissionMap.get(role))
                .map(events -> events.contains(event))
                .orElse(false);
    }
}
