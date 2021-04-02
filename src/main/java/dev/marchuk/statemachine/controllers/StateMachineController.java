package dev.marchuk.statemachine.controllers;

import dev.marchuk.statemachine.domain.Event;
import dev.marchuk.statemachine.domain.ActivityState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StateMachineController {

    private final StateMachine<ActivityState, Event> stateMachine;

    @GetMapping("/api/status")
    public String status(String event) {
        var currentState = stateMachine.getState();
        var transition = Event.valueOf(event);
        stateMachine.sendEvent(transition);
        var newState = stateMachine.getState();
        return String.format("%s -> %s", currentState.getId().name(), newState.getId().name());
    }
}
