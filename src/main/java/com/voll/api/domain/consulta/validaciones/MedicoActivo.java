package com.voll.api.domain.consulta.validaciones;

import com.voll.api.domain.consulta.DatosAgendarConsulta;
import com.voll.api.domain.medico.MedicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MedicoActivo implements ValidadorDeConsultas{

    @Autowired
    private MedicoRepository medicoRepository;
    public void validar(DatosAgendarConsulta datosAgendarConsulta){
        if (datosAgendarConsulta.idMedico()==null){
            return;
        }
        var medicoActivo = medicoRepository.findActivoById(datosAgendarConsulta.idMedico());
        if(!medicoActivo){
            throw new ValidationException("No se permite agendar citas con medicos inactivos en el sistema");
        }
    }
}
