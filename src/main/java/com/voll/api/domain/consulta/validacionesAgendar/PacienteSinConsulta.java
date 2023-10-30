package com.voll.api.domain.consulta.validacionesAgendar;

import com.voll.api.domain.consulta.ConsultaRepository;
import com.voll.api.domain.consulta.DatosAgendarConsulta;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PacienteSinConsulta implements ValidadorAgendaConsultas {

    @Autowired
    private ConsultaRepository consultaRepository;
    public void validar(DatosAgendarConsulta datosAgendarConsulta){
        var primerHorario = datosAgendarConsulta.fecha().withHour(7);
        var ultimoHorario = datosAgendarConsulta.fecha().withHour(18);

        var pacienteConsulta = consultaRepository.existsByPacienteIdAndFechaBetween(datosAgendarConsulta.idPaciente(),primerHorario,ultimoHorario);
        var consultaActiva = consultaRepository.existsByConsultaActiva(datosAgendarConsulta.idPaciente()); //.stream().anyMatch(Boolean::booleanValue);
        System.out.println(pacienteConsulta);
        System.out.println(consultaActiva);
        if(pacienteConsulta && consultaActiva){
            throw new ValidationException("No permita programar más de una consulta en el mismo día para el mismo paciente");
        }
    }
}
