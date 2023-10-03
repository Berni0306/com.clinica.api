package com.voll.api.domain.consulta.validaciones;

import com.voll.api.domain.consulta.ConsultaRepository;
import com.voll.api.domain.consulta.DatosAgendarConsulta;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoConConsulta implements ValidadorDeConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;
    public void validar(DatosAgendarConsulta datosAgendarConsulta){

        var medicoConsulta = consultaRepository.existsByMedicoIdAndFecha(datosAgendarConsulta.idMedico(),datosAgendarConsulta.fecha());
        if(medicoConsulta){
            throw new ValidationException("Este medico ya tiene una consulta en ese horario");
        }
    }
}
