package dev.marchuk.statemachine.service;

import dev.marchuk.statemachine.config.StateTransitionPermissions;
import dev.marchuk.statemachine.domain.ActivityState;
import dev.marchuk.statemachine.domain.Event;
import dev.marchuk.statemachine.domain.Role;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class TransitionPermissionChecker {
    private final Map<ActivityState, StateTransitionPermissions> statePermissionsMap;

    public TransitionPermissionChecker() {
        statePermissionsMap = new HashMap<>();
    }

    public void addPermissions(ActivityState state, StateTransitionPermissions permissions) {
        statePermissionsMap.put(state, permissions);
    }

    public boolean isPermitted(Role role, ActivityState state, Event transition) {
        return Optional.ofNullable(statePermissionsMap.get(state))
                .map(statePermissions -> statePermissions.isPermitted(role, transition))
                .orElse(false);
    }
}
