package com.github.aclijpio.animalcare;

import org.quartz.*;

public class Testings {

    public static void main(String[] args) {

        try {
            SchedulerFactory schedulerFactory = new org.quartz.impl.StdSchedulerFactory();
            Scheduler scheduler = schedulerFactory.getScheduler();

            // Создайте описание работы (job)
            JobDetail jobDetail = JobBuilder.newJob(MyJob.class)
                    .withIdentity("myJob", "group1")
                    .build();

            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 51 21 * * ?"))
                    .build();
            CronTrigger cronTrigger2 = TriggerBuilder.newTrigger()
                    .withIdentity("myTrigger2", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 52 21 * * ?"))
                    .build();

            // Запустите планировщик
            scheduler.start();
            scheduler.scheduleJob(jobDetail, cronTrigger);
            scheduler.scheduleJob(jobDetail, cronTrigger2);

        } catch (SchedulerException e) {
            System.out.println(e);
        }


    }

}
