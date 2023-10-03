package com.voll.api.domain.medico;

import com.voll.api.domain.direccion.DatosDireccion;

public record DatosRespuestaMedico (Long id, String nombre, String email, String telfono,
                                    String documento, DatosDireccion direccion){
}
