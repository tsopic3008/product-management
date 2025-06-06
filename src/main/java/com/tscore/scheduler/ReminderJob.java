package com.tscore.scheduler;

import jakarta.enterprise.context.ApplicationScoped;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.jboss.logging.Logger;

@ApplicationScoped
public class ReminderJob implements Job {

    private static final Logger LOG = Logger.getLogger(ReminderJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOG.info("📅 MySimpleJob executed at: " + java.time.LocalDateTime.now());
    }
}
