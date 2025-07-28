package com.tscore.client;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/mail")
@RegisterRestClient(configKey = "mailing-service")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface MailingClient {

    @POST
    Response sendWelcomeEmail(MailRequest mailRequest);

    class MailRequest {
        public String email;
        public String username;

        public MailRequest(String email, String username) {
            this.email = email;
            this.username = username;
        }
    }
}