package com.tscore.util;

import com.tscore.event.LogMessageEvent;
import jakarta.enterprise.event.Observes;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailNotifier {

    public void onLogReceived(@Observes LogMessageEvent event) {
        if(event.message != null && event.message.equals("ALERT")) {
            log.info("📢 ALERT TRIGGERED! -> " + event.message);
        }
    }
}
