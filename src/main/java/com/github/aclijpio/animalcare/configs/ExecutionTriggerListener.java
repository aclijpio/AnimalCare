package com.github.aclijpio.animalcare.configs;

import com.github.aclijpio.animalcare.exceptions.TriggerMisfiredException;
import lombok.extern.log4j.Log4j2;
import org.quartz.JobExecutionContext;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

@Log4j2
public class ExecutionTriggerListener implements TriggerListener {
    private final Stages stages = new Stages();


    @Override
    public String getName() {
        return ExecutionTriggerListener.class.getName();
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
        log.error("Trigger misfired {} due to a scheduling issue", trigger.getKey(), new TriggerMisfiredException("Trigger misfired"));
    }

    @Override
    public void triggerComplete(Trigger trigger, JobExecutionContext jobExecutionContext, Trigger.CompletedExecutionInstruction completedExecutionInstruction) {
        log.info("Trigger completed {}", completedExecutionInstruction);

        stages.shift(trigger);
    }


    public Trigger getPrevTrigger(){
        return stages.prev;
    }
    public Trigger getCurrentTrigger(){
        return stages.current;
    }

    private static class Stages {
        private Trigger prev;
        private Trigger current;

        public void shift(Trigger trigger){
            this.prev = current;
            this.current = trigger;
        }
    }

}
