package dev.marchuk.statemachine.config;

import dev.marchuk.statemachine.domain.Event;
import dev.marchuk.statemachine.domain.State;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class StateMachineConfig extends EnumStateMachineConfigurerAdapter<State, Event> {

    @Override
    public void configure(StateMachineStateConfigurer<State, Event> states) throws Exception {
        states.withStates()
                .initial(State.CREATED)
                .states(EnumSet.allOf(State.class));
    }

    @Override
    public void configure(final StateMachineConfigurationConfigurer<State, Event> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<State, Event> transitions) throws Exception {
        transitions.withExternal()
//                CREATED
                .source(State.CREATED).target(State.EDITED)
                .event(Event.EDIT)
                .and()

                .withExternal()
                .source(State.CREATED).target(State.DELETED)
                .event(Event.DELETE)
                .and()

                .withExternal()
                .source(State.CREATED).target(State.WAIT_FOR_PROVIDER_APPROVE)
                .event(Event.SEND_FOR_APPROVE)
                .and()

                .withExternal()
                .source(State.CREATED).target(State.PUBLISHED)
                .event(Event.PUBLISH)
                .and()
//              EDITED
                .withExternal()
                .source(State.EDITED).target(State.WAIT_FOR_PROVIDER_APPROVE)
                .event(Event.SEND_FOR_APPROVE)
                .and()

                .withInternal()
                .source(State.EDITED)
                .event(Event.EDIT)
                .and()

                .withExternal()
                .source(State.EDITED).target(State.PUBLISHED)
                .event(Event.PUBLISH)
                .and()
//              WAIT_FOR_PROVIDER_APPROVE
                .withExternal()
                .source(State.WAIT_FOR_PROVIDER_APPROVE).target(State.PROVIDER_APPROVED)
                .event(Event.APPROVE)
                .and()

                .withExternal()
                .source(State.WAIT_FOR_PROVIDER_APPROVE).target(State.PROVIDER_DECLINED)
                .event(Event.REJECT)
                .and()
//              PROVIDER_APPROVED
                .withExternal()
                .source(State.PROVIDER_APPROVED).target(State.PUBLISHED)
                .event(Event.PUBLISH)
                .and()
//              PROVIDER_DECLINED
                .withExternal()
                .source(State.PROVIDER_DECLINED).target(State.EDITED)
                .event(Event.EDIT)
                .and()
//              PUBLISHED
                .withExternal()
                .source(State.PUBLISHED).target(State.ARCHIVED)
                .event(Event.ARCHIVE)
                .and()

                .withExternal()
                .source(State.PUBLISHED).target(State.EDITED)
                .event(Event.EDIT)
                .and()
//              ARCHIVED
                .withExternal()
                .source(State.ARCHIVED).target(State.EDITED)
                .event(Event.RESTORE)
                .and()
        ;
    }

}
