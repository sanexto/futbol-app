package com.ar.duxsoftware.futbol.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EquipoRequest(
        @NotNull @Size(min = 1, max = 100) String nombre,
        @NotNull @Size(min = 1, max = 100) String liga,
        @NotNull @Size(min = 1, max = 100) String pais
) {}
