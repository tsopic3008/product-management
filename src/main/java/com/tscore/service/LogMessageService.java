package com.tscore.service;

import com.tscore.event.LogMessageEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import com.tscore.model.LogMessage;

@ApplicationScoped
public class LogMessageService {
    @Inject
    Event<LogMessageEvent> logMessageEvent;

    public void logMessage(LogMessage logMessage) {
        logMessageEvent.fire(new LogMessageEvent(logMessage.message));
    }
}
