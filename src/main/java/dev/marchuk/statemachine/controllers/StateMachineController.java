package dev.marchuk.statemachine.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StateMachineController {

    @GetMapping("/api/status")
    public String status() {
        return "ok";
    }
}
