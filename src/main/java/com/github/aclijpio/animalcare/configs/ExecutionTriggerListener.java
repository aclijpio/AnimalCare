package com.github.aclijpio.animalcare.configs;

import lombok.Getter;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

@Getter
public class ExecutionTriggerListener implements TriggerListener {

    private Trigger prev;
    private Trigger next;
    private Trigger current;


    @Override
    public String getName() {
        return "ExecutionTriggerListener";
    }

    @Override
    public void triggerFired(Trigger trigger, JobExecutionContext jobExecutionContext) {

    }

    @Override
    public boolean vetoJobExecution(Trigger trigger, JobExecutionContext jobExecutionContext) {
        return false;
    }

    @Override
    public void triggerMisfired(Trigger trigger) {

    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {

        System.out.println("Jfdshaifbaj");

        this.prev = trigger;
    }
}
