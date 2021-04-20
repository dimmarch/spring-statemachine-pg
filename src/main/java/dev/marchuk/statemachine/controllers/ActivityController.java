package dev.marchuk.statemachine.controllers;

import dev.marchuk.statemachine.dao.ActivityRepository;
import dev.marchuk.statemachine.domain.Activity;
import dev.marchuk.statemachine.domain.Event;
import dev.marchuk.statemachine.domain.Role;
import dev.marchuk.statemachine.service.ActivityTransitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

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

        var activity = activityRepository.getActivity(activityId);
        if (activity == null) {
            activity = activityRepository.createActivity();
        }
        if (transition != null) {
            try {
                activityTransitionService.makeTransition(role, activity.getId(), transition);
            } catch (Exception e) {
                model.addAttribute("errorMessage", e.getMessage());
            }
        }
        model.addAttribute("activityId", activity.getId());
        model.addAttribute("state", activity.getState().name());
        model.addAttribute("role", role.name());
        model.addAttribute("state", activity.getState().name());
        var events = activityTransitionService.getAvailableTransitions(role, activity);
        model.addAttribute("permittedEvents", events);
        model.addAttribute("events", List.of(Event.values()));
        model.addAttribute("roles", Role.values());
        var activityIds = activityRepository.getActivities().stream()
                .map(Activity::getId)
                .collect(Collectors.toList());
        model.addAttribute("activityIds", activityIds);
        return "activity";
    }


}
