package com.github.aclijpio.animalcare.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzSchedulerCustomizer implements SchedulerFactoryBeanCustomizer {

    private final ExecutionTriggerListener executionTriggerListener;

    @Autowired
    public QuartzSchedulerCustomizer(ExecutionTriggerListener executionTriggerListener) {
        this.executionTriggerListener = executionTriggerListener;
    }

    @Override
    public void customize(SchedulerFactoryBean schedulerFactoryBean) {
        schedulerFactoryBean.setGlobalTriggerListeners(executionTriggerListener);

    }
}
