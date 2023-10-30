package com.voll.api.domain.consulta.validacionesAgendar;

import com.voll.api.domain.consulta.DatosAgendarConsulta;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class HorarioAgendaAnticipacion implements ValidadorAgendaConsultas {

    public void validar(DatosAgendarConsulta datosAgendarConsulta){
        var ahora = LocalDateTime.now();
        var horaDeConsulta = datosAgendarConsulta.fecha();

        var diferenciaDe30Min = Duration.between(ahora,horaDeConsulta).toMinutes()<30;
        if (diferenciaDe30Min){
            throw new ValidationException("Las consultas deben programarse con al menos 30 minutos de anticipació");
        }
    }
}
