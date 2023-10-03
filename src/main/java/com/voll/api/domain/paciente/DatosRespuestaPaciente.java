package com.voll.api.domain.paciente;

import com.voll.api.domain.direccion.DatosDireccion;

public record DatosRespuestaPaciente (Long id, String nombre, String email, String telfono,
                                     String documento, DatosDireccion direccion){
}
