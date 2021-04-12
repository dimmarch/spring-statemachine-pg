package dev.marchuk.statemachine.controllers;

import dev.marchuk.statemachine.dao.ActivityRepository;
import dev.marchuk.statemachine.domain.Event;
import dev.marchuk.statemachine.domain.Role;
import dev.marchuk.statemachine.service.ActivityTransitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ActivityController {

    private final ActivityTransitionService activityTransitionService;
    private final ActivityRepository activityRepository;

    @GetMapping("/activity")
    public String activityPage(Integer activityId, Event transition, Role role, Model model) {
        if (role == null) {
            role = Role.ADMIN;
        }

        var activity = activityId == null ? activityRepository.createActivity() : activityRepository.getActivity(activityId);
        if (transition != null) {
            activityTransitionService.makeTransition(role, activity.getId(), transition);
        }
        model.addAttribute("activityId", activity.getId());
        model.addAttribute("state", activity.getState().name());
        model.addAttribute("role", role.name());
        model.addAttribute("state", activity.getState().name());
        var events = activityTransitionService.getAvailableTransitions(role, activity);
        model.addAttribute("events", events);
        return "activity";
    }


}
