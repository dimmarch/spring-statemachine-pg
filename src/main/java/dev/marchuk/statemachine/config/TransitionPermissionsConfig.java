package dev.marchuk.statemachine.config;

import dev.marchuk.statemachine.config.state.StateTransitionConfigurer;
import dev.marchuk.statemachine.service.TransitionPermissionChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class TransitionPermissionsConfig {

    private final List<StateTransitionConfigurer> transitionConfigList;

    @Bean
    public TransitionPermissionChecker transitionPermissionChecker() {
        var checker = new TransitionPermissionChecker();
        for (var transitionConfig : transitionConfigList) {
            checker.addPermissions(transitionConfig.getState(), transitionConfig.getPermissions());
        }
        return checker;
    }
}
