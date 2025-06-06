package com.tscore.util;

import com.tscore.event.LogMessageEvent;
import jakarta.enterprise.event.Observes;
import lombok.extern.slf4j.Slf4j;
@Slf4j
public class Logger {

    public void onLogReceived(@Observes LogMessageEvent event) {
        log.info("Observed: " + event.message);
    }
}
