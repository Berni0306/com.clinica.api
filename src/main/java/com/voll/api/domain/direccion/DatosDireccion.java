package com.voll.api.domain.direccion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DatosDireccion(
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9 ]+$")
        String calle,
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9 ]+$")
        String colonia,
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9 ]+$")
        String ciudad,
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z0-9 ]+$")
        String estado,
        @NotBlank
        @Pattern(regexp = "^[0-9]+$")
        @Pattern(regexp = "\\d{5}")
        String cp) {
}
