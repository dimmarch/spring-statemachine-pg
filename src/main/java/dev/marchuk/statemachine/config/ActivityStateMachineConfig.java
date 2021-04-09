package dev.marchuk.statemachine.config;

import dev.marchuk.statemachine.config.statemachine.ActivityStateMachineApplicationListener;
import dev.marchuk.statemachine.domain.Event;
import dev.marchuk.statemachine.domain.ActivityState;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class ActivityStateMachineConfig extends EnumStateMachineConfigurerAdapter<ActivityState, Event> {

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
        transitions.withExternal()
//                CREATED
                .source(ActivityState.CREATED).target(ActivityState.EDITED)
                .event(Event.EDIT)
                .and()

                .withExternal()
                .source(ActivityState.CREATED).target(ActivityState.DELETED)
                .event(Event.DELETE)
                .and()

                .withExternal()
                .source(ActivityState.CREATED).target(ActivityState.WAIT_FOR_PROVIDER_APPROVE)
                .event(Event.SEND_FOR_APPROVE)
                .and()

                .withExternal()
                .source(ActivityState.CREATED).target(ActivityState.PUBLISHED)
                .event(Event.PUBLISH)
                .and()
//              EDITED
                .withExternal()
                .source(ActivityState.EDITED).target(ActivityState.WAIT_FOR_PROVIDER_APPROVE)
                .event(Event.SEND_FOR_APPROVE)
                .and()

                .withInternal()
                .source(ActivityState.EDITED)
                .event(Event.EDIT)
                .and()

                .withExternal()
                .source(ActivityState.EDITED).target(ActivityState.PUBLISHED)
                .event(Event.PUBLISH)
                .and()
//              WAIT_FOR_PROVIDER_APPROVE
                .withExternal()
                .source(ActivityState.WAIT_FOR_PROVIDER_APPROVE).target(ActivityState.PROVIDER_APPROVED)
                .event(Event.APPROVE)
                .and()

                .withExternal()
                .source(ActivityState.WAIT_FOR_PROVIDER_APPROVE).target(ActivityState.PROVIDER_DECLINED)
                .event(Event.REJECT)
                .and()
//              PROVIDER_APPROVED
                .withExternal()
                .source(ActivityState.PROVIDER_APPROVED).target(ActivityState.PUBLISHED)
                .event(Event.PUBLISH)
                .and()
//              PROVIDER_DECLINED
                .withExternal()
                .source(ActivityState.PROVIDER_DECLINED).target(ActivityState.EDITED)
                .event(Event.EDIT)
                .and()
//              PUBLISHED
                .withExternal()
                .source(ActivityState.PUBLISHED).target(ActivityState.ARCHIVED)
                .event(Event.ARCHIVE)
                .and()

                .withExternal()
                .source(ActivityState.PUBLISHED).target(ActivityState.EDITED)
                .event(Event.EDIT)
                .and()
//              ARCHIVED
                .withExternal()
                .source(ActivityState.ARCHIVED).target(ActivityState.EDITED)
                .event(Event.RESTORE)
                .and()
        ;
    }

}
