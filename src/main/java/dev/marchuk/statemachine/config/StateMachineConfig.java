package dev.marchuk.statemachine.config;

import dev.marchuk.statemachine.domain.Events;
import dev.marchuk.statemachine.domain.States;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<States, Events> {

    @Override
    public void configure(StateMachineStateConfigurer<States, Events> states) throws Exception {
        states.withStates()
                .initial(States.CREATED)
                .states(EnumSet.allOf(States.class));
    }

    @Override
    public void configure(final StateMachineConfigurationConfigurer<States, Events> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<States, Events> transitions) throws Exception {
        transitions.withExternal()
                .source(States.CREATED).target(States.EDITED)
                .event(Events.EDIT)
                .and()

                .withExternal()
                .source(States.CREATED).target(States.DELETED)
                .event(Events.DELETE)
                .and()

                .withExternal()
                .source(States.CREATED).target(States.WAIT_FOR_PROVIDER_APPROVE)
                .event(Events.SEND_FOR_APPROVE)
                .and()

                .withExternal()
                .source(States.CREATED).target(States.PUBLISHED)
                .event(Events.PUBLISH)
                .and()

                .withExternal()
                .source(States.EDITED).target(States.WAIT_FOR_PROVIDER_APPROVE)
                .event(Events.SEND_FOR_APPROVE)

        ;
    }

}
