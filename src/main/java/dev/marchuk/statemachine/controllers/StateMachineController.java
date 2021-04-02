package dev.marchuk.statemachine.controllers;

import dev.marchuk.statemachine.domain.Event;
import dev.marchuk.statemachine.domain.State;
import lombok.RequiredArgsConstructor;
import org.springframework.statemachine.StateMachine;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StateMachineController {

    private final StateMachine<State, Event> stateMachine;

    @GetMapping("/api/status")
    public String status(String event) {
        var currentState = stateMachine.getState();
        var transition = Event.valueOf(event);
        stateMachine.sendEvent(transition);

        return currentState.getId().name();
    }
}
