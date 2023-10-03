package com.voll.api.domain.consulta.validaciones;

import com.voll.api.domain.consulta.DatosAgendarConsulta;
import com.voll.api.domain.paciente.Paciente;
import com.voll.api.domain.paciente.PacienteRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteActivo implements ValidadorDeConsultas{

    @Autowired
    private PacienteRepository pacienteRepository;
    public void validar(DatosAgendarConsulta datosAgendarConsulta){
        if (datosAgendarConsulta.idPaciente()==null){
            return;
        }
        var pacienteActivo = pacienteRepository.findActivoById(datosAgendarConsulta.idPaciente());
        if(!pacienteActivo){
            throw new ValidationException("No se permite agendar citas con pacientes inactivos en el sistema");
        }
    }
}
