package dev.marchuk.statemachine.dao;

import dev.marchuk.statemachine.domain.Activity;
import dev.marchuk.statemachine.domain.ActivityState;
import dev.marchuk.statemachine.domain.Provider;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Component
public class ActivityRepository {
    private Map<Integer, Activity> activityMap = new HashMap<>();

    public ActivityRepository() {
        createActivity();
        createActivity();
    }

    public Activity createActivity() {
        var id = activityMap.keySet().size() + 1;
        var activity = Activity.builder()
                .id(id)
                .state(ActivityState.CREATED)
                .name("Activity #" + id)
                .description("activity number " + id + " is not that bad")
                .duration(Duration.ofMinutes(42))
                .provider(createProvider(id))
                .build();
        saveActivity(activity);
        return activity;
    }

    public Provider createProvider(Integer id) {
        return Provider.builder()
                .id(id)
                .name("Provider #" + id)
                .email("email@provider" + id + ".ru")
                .build();
    }

    public Activity getActivity(Integer activityId) {
        return activityMap.get(activityId);
    }

    public Activity saveActivity(Activity activity) {
        return activityMap.put(activity.getId(), activity);
    }
}
