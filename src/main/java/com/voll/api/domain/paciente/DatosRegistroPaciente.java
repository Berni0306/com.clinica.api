package com.voll.api.domain.paciente;

import com.voll.api.domain.direccion.DatosDireccion;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DatosRegistroPaciente (
        @NotBlank
        @Pattern(regexp = "^[a-zA-Z ]+$")
        String nombre,
        @NotBlank
        @Email
        String email,
        @NotBlank
        @Pattern(regexp = "\\d{10}")
        @Pattern(regexp = "^[0-9]+$")
        String telefono,
        @NotBlank
        @Pattern(regexp = "^[0-9]+$")
        @Pattern(regexp = "\\d{6}")
        String documentoIdentidad,
        @NotNull
        @Valid
        DatosDireccion direccion){
}
