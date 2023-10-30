package com.voll.api.domain.consulta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    Boolean existsByPacienteIdAndFechaBetween(Long idPaciente, LocalDateTime primerHorario, LocalDateTime ultimoHorario);
    Boolean existsByMedicoIdAndFecha(Long idMedico, LocalDateTime fecha);
    Page<Consulta> findByActivoTrue(Pageable paginacion);

    @Query("""
            select c.activo from Consulta c
            where
            c.paciente.id = :idPaciente
            """)
    Boolean existsByConsultaActiva(Long idPaciente);
}
