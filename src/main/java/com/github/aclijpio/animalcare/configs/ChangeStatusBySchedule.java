package com.github.aclijpio.animalcare.configs;

import com.github.aclijpio.animalcare.services.AnimalsStatusClient;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalTime;

@RequiredArgsConstructor
public class ChangeStatusBySchedule implements Job {

    private AnimalsStatusClient statusClient;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        statusClient.updateStatus(jobExecutionContext.getTrigger().getDescription());
    }
}
