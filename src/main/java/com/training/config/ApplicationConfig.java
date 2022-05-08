package com.training.config;

import com.training.model.enums.State;
import com.training.service.action.UpdateStatusAction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
public class ApplicationConfig {

    @Bean
    public Map<State, UpdateStatusAction> generateActionMap(List<UpdateStatusAction> actions) {
        return actions
                .stream()
                .collect(Collectors.toMap(UpdateStatusAction::getState, Function.identity()));
    }
}
