package dev.marchuk.statemachine.controllers;

import dev.marchuk.statemachine.domain.Event;
import dev.marchuk.statemachine.domain.ActivityState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineEventResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class StateMachineController {

    private final StateMachine<ActivityState, Event> stateMachine;

    @GetMapping("/api/event")
    public String event(String event) {
        var currentState = stateMachine.getState();
        var transition = Event.valueOf(event);
        var results = stateMachine.sendEvent(Mono.just(MessageBuilder
                .withPayload(transition).build())).collectList().block();

        if(results.get(0).getResultType().equals(StateMachineEventResult.ResultType.DENIED)) {
            return "transition denied";
        }

        var newState = stateMachine.getState();
        return String.format("%s -> %s", currentState.getId().name(), newState.getId().name());
    }
}
