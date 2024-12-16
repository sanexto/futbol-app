package com.ar.duxsoftware.futbol.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @NotNull @Size(min = 1, max = 50) String username,
        @NotNull @Size(min = 1, max = 50) String password
) {}
