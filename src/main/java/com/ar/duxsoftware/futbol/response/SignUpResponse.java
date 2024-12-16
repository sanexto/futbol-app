package com.ar.duxsoftware.futbol.response;

public class SignUpResponse {
    private Integer id;
    private String username;

    public SignUpResponse() {}

    public SignUpResponse(final Integer id, final String username) {
        this.id = id;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }
}
