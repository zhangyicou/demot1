package com.example.demot1.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Author: yichu.zhang
 * @Date: 2019-09-26 14:11
 */
@Slf4j
@Component
@ConditionalOnProperty(prefix = "switch", name = "scheduler.dynamic", havingValue = "1", matchIfMissing = false)
public class DynamicScheduler implements SchedulingConfigurer {
    private static String CRON = "0/2 * * * * ?";
    private static int i = 0;

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.addTriggerTask(new Runnable() {
            @Override
            public void run() {
                i++;
                log.info("i = {}, time = {}", i, new Date());
                if(i % 3 ==0){
                    CRON = "0/20 * * * * ?";
                }else{
                    CRON = "0/10 * * * * ?";
                }
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {

                CronTrigger trigger = new CronTrigger(CRON);
                Date nextTime = trigger.nextExecutionTime(triggerContext);
                log.info("CRON = {}, nex exceute = {}", CRON, nextTime);
                return nextTime;
            }
        });
    }
}
