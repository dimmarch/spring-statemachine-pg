package dev.marchuk.statemachine.service;

import dev.marchuk.statemachine.config.StateTransitionPermissions;
import dev.marchuk.statemachine.domain.ActivityState;

import java.util.HashMap;
import java.util.Map;

public class TransitionPermissionChecker {
    private final Map<ActivityState, StateTransitionPermissions> statePermissionsMap;

    public TransitionPermissionChecker() {
        statePermissionsMap = new HashMap<>();
    }

    public void addPermissions(ActivityState state, StateTransitionPermissions permissions) {
        statePermissionsMap.put(state, permissions);
    }
}
