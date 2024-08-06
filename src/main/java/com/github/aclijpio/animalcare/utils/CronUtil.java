package com.github.aclijpio.animalcare.utils;

import com.cronutils.builder.CronBuilder;
import com.cronutils.model.Cron;
import com.cronutils.model.CronType;
import com.cronutils.model.definition.CronDefinitionBuilder;

import java.time.LocalTime;

import static com.cronutils.model.field.expression.FieldExpressionFactory.*;


public enum CronUtil {

    UTIL;

    private static final CronType CRON_TYPE = CronType.QUARTZ;


    public String convertLocalTimeToCron(LocalTime time) {
        int hour = time.getHour();
        int minute = time.getMinute();
        int second = time.getSecond();

        Cron cron = CronBuilder.cron(CronDefinitionBuilder.instanceDefinitionFor(CRON_TYPE))
                .withSecond(on(second))
                .withMinute(on(minute))
                .withHour(on(hour))
                .withDoM(always())
                .withMonth(always())
                .withDoW(questionMark())
                .withYear(always())
                .instance();

        return cron.asString();
    }

}
