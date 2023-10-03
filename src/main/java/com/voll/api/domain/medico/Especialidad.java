package com.voll.api.domain.medico;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Especialidad {

    ORTODONCIA("Ortodoncia"),
    REHABILITACION("Rehabilitacion"),
    ENDODONCIA("Endodoncia"),
    PEDIATRIA("Pediatria");

    private String dato;
    private Especialidad(String dato) {
        this.dato = dato;
    }
    @JsonValue
    public String getDato(){
        return dato;
    }
    @JsonCreator
    public static Especialidad from(String value){
        for (Especialidad especialidad : Especialidad.values()){
            if (especialidad.dato.equalsIgnoreCase(value)){
                return especialidad;
            }
        }
        throw new IllegalArgumentException("Valor no valido para Especialidad " + value);
    }
}
