package com.tscore.endpoint;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import com.tscore.model.LogMessage;
import com.tscore.service.LogMessageService;

@Path("/log")
public class LogEndpoint {
    @Inject
    LogMessageService logMessageService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response postLogMessage (LogMessage logMessage) {
        logMessageService.logMessage(logMessage);
        return Response.ok().build();
    }
}
