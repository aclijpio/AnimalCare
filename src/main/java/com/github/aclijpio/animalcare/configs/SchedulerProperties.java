package com.github.aclijpio.animalcare.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerProperties {

    @Getter
    @Value("${spring.quartz.properties.org.quartz.jobStore.tablePrefix}")
    private static String tablePrefix;

}
