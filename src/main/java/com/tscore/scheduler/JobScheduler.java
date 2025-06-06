package com.tscore.scheduler;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.quartz.*;
import org.jboss.logging.Logger;

@ApplicationScoped
public class JobScheduler {

    private static final Logger LOG = Logger.getLogger(JobScheduler.class);

    @Inject
    Scheduler quartz;

    @PostConstruct
    void init() {
        try {
            JobDetail job = JobBuilder.newJob(ReminderJob.class)
                    .withIdentity("reminderJob", "group1")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("reminderTrigger", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0 23 * * ?"))
                    .build();

            quartz.scheduleJob(job, trigger);
            LOG.info("✅ ReminderJob scheduled.");
        } catch (SchedulerException e) {
            LOG.error("❌ Failed to schedule ReminderJob", e);
        }
    }
}

