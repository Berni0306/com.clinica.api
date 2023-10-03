package com.voll.api.domain.direccion;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Direccion {

    private String calle;
    private String colonia;
    private String ciudad;
    private String estado;
    private String cp;

    public Direccion(DatosDireccion direccion) {
        this.calle = direccion.calle();
        this.colonia = direccion.colonia();
        this.ciudad = direccion.ciudad();
        this.estado = direccion.estado();
        this.cp = direccion.cp();
    }

    public Direccion actualizarDatos(DatosDireccion direccion) {
        this.calle = direccion.calle();
        this.colonia = direccion.colonia();
        this.ciudad = direccion.ciudad();
        this.estado = direccion.estado();
        this.cp = direccion.cp();
        return this;
    }
}
