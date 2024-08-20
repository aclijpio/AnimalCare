package com.github.aclijpio.animalcare.configs;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SchedulerConfig {
    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(ChangeStatusBySchedule.class)
                .withIdentity("customJob")
                .storeDurably()
                .build();
    }
    @Bean
    public ExecutionTriggerListener triggerListener() {
        return new ExecutionTriggerListener();
    }
}
