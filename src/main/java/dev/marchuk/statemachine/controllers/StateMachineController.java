package dev.marchuk.statemachine.controllers;

import dev.marchuk.statemachine.domain.Event;
import dev.marchuk.statemachine.service.ActivityTransitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StateMachineController {

    private final ActivityTransitionService activityTransitionService;

    @GetMapping("/api/event")
    public String event(String event) {
        var transition = Event.valueOf(event);
        return activityTransitionService.makeTransition(transition);
    }
}
