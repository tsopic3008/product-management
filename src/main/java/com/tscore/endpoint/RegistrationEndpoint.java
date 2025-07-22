package com.tscore.endpoint;


import com.tscore.service.KeycloakService;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.net.URI;
import java.net.http.*;
import java.util.Map;

@Path("/register")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegistrationEndpoint {

    @Inject
    KeycloakService keycloakService;

    private static final String KEYCLOAK_BASE_URL = "http://localhost:8080";
    private static final String REALM = "tscore";

    @POST
    public Response register(RegisterRequest req) {
        try {
            String token = keycloakService.getServiceAccountToken();

            JsonObjectBuilder userBuilder = Json.createObjectBuilder()
                    .add("username", req.username)
                    .add("enabled", true)
                    .add("emailVerified", true)
                    .add("email", req.email)
                    .add("firstName", req.firstName)
                    .add("lastName", req.lastName)
                    .add("credentials", Json.createArrayBuilder().add(
                            Json.createObjectBuilder()
                                    .add("type", "password")
                                    .add("value", req.password)
                                    .add("temporary", false)
                    ));

            JsonObject userPayload = userBuilder.build();

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(KEYCLOAK_BASE_URL + "/admin/realms/" + REALM + "/users"))
                    .header("Authorization", "Bearer " + token)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(userPayload.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201) {
                return Response.ok(Map.of("message", "User successfully registered")).build();
            } else {
                return Response.status(response.statusCode())
                        .entity(Map.of("error", "Keycloak error", "details", response.body()))
                        .build();
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500).entity(Map.of("error", "Internal error")).build();
        }
    }

    public static class RegisterRequest {
        public String username;
        public String password;
        public String email;
        public String firstName;
        public String lastName;
    }
}

