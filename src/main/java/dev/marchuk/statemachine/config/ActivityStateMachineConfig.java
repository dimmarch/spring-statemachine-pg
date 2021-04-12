package dev.marchuk.statemachine.config;

import dev.marchuk.statemachine.config.listner.ActivityStateMachineApplicationListener;
import dev.marchuk.statemachine.config.state.StateTransitionConfigurer;
import dev.marchuk.statemachine.domain.Event;
import dev.marchuk.statemachine.domain.ActivityState;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;
import java.util.List;

@Configuration
@EnableStateMachineFactory
@RequiredArgsConstructor
public class ActivityStateMachineConfig extends EnumStateMachineConfigurerAdapter<ActivityState, Event> {

    private final List<StateTransitionConfigurer> transitionConfigList;

    @Override
    public void configure(StateMachineStateConfigurer<ActivityState, Event> states) throws Exception {
        states.withStates()
                .initial(ActivityState.CREATED)
                .states(EnumSet.allOf(ActivityState.class));
    }

    @Override
    public void configure(final StateMachineConfigurationConfigurer<ActivityState, Event> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true)
                .listener(new ActivityStateMachineApplicationListener());
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<ActivityState, Event> transitions) throws Exception {
        for (var transitionConfig : transitionConfigList) {
            transitionConfig.configureTransitions(transitions);
        }
    }
}
