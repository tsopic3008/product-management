package com.tscore.keycloak;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AccessTokenResource {
    @JsonProperty("access_token")
    public String accessToken;

    @JsonProperty("expires_in")
    public int expiresIn;
}

