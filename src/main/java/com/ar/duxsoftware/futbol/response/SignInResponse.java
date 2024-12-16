package com.ar.duxsoftware.futbol.response;

public class SignInResponse {
    private String token;

    public SignInResponse() {}

    public SignInResponse(final String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }
}
