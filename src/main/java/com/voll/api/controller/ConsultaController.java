package com.voll.api.controller;

import com.voll.api.domain.consulta.*;
import com.voll.api.domain.medico.DatosListadoMedico;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {

    @Autowired
    private AgendaDeConsultaService agendaDeConsultaService;

    @Autowired
    private ConsultaRepository consultaRepository;

    @PostMapping
    @Transactional
    public ResponseEntity agendar(@RequestBody @Valid DatosAgendarConsulta datosAgendarConsulta){
        var response =  agendaDeConsultaService.agendar(datosAgendarConsulta);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<DatosDetalleConsulta>> listadoConsultas(@PageableDefault(size = 2) Pageable paginacion){
        return ResponseEntity.ok(consultaRepository.findByActivoTrue(paginacion).map(DatosDetalleConsulta::new));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarConsulta(@PathVariable Long id, @RequestBody @Valid MotivoCancelacion motivoCancelacion){
        Consulta consulta = consultaRepository.getReferenceById(id);
        agendaDeConsultaService.cancelar(consulta, motivoCancelacion);
        return ResponseEntity.noContent().build();
    }
}
