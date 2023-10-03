package com.voll.api.controller;

import com.voll.api.domain.consulta.AgendaDeConsultaService;
import com.voll.api.domain.consulta.DatosAgendarConsulta;
import com.voll.api.domain.consulta.DatosDetalleConsulta;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultaService agendaDeConsultaService;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datosAgendarConsulta){
        agendaDeConsultaService.agendar(datosAgendarConsulta);
        return ResponseEntity.ok(new DatosDetalleConsulta(null, null, null, null));
    }

}
