package ru.homework;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.homework.aspect.TaskAspect;
import ru.homework.properties.LoggerProperties;

@Configuration
@EnableConfigurationProperties(LoggerProperties.class)
public class CustomAutoConfiguration {

    @Bean
    @ConditionalOnProperty(
            value = "logs.example.enable",
            havingValue = "true"
    )
    public TaskAspect taskAspect(LoggerProperties loggerProperties) {
        return new TaskAspect(loggerProperties);
    }
}
