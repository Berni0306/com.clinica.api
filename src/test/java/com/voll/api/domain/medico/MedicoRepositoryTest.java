package com.voll.api.domain.medico;

import com.voll.api.domain.consulta.Consulta;
import com.voll.api.domain.direccion.DatosDireccion;
import com.voll.api.domain.paciente.DatosRegistroPaciente;
import com.voll.api.domain.paciente.Paciente;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.cglib.core.Local;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Deberia de retornar null cuando el medico se encuentre en consulta con otro paciente en ese horario")
    void seleccionarMedicoConEspecialidadEnFechaEscenario1() {

        //given
        var proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);
        var medico = registrarMedico("Jose", "j@mail.com", "123456", Especialidad.PEDIATRIA);
        var paciente = registrarPaciente("Berni", "b@mail.com", "654321");
        registrarConsulta(medico, paciente, proximoLunes10H);

        //when
        var medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.PEDIATRIA, proximoLunes10H);

        //then
        assertThat(medicoLibre).isNull();
    }

    @Test
    @DisplayName("Deberia de retornar un medico cuando realice la consulta en la base de datos para ese horario")
    void seleccionarMedicoConEspecialidadEnFechaEscenario2() {

        //given
        var proximoLunes10H = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10,0);

        //when
        var medico = registrarMedico("Jose", "j@mail.com", "123456", Especialidad.PEDIATRIA);
        var medicoLibre = medicoRepository.seleccionarMedicoConEspecialidadEnFecha(Especialidad.PEDIATRIA, proximoLunes10H);

        //then
        assertThat(medicoLibre).isEqualTo(medico);
    }

    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha){
        em.persist(new Consulta(null, medico, paciente, fecha));
    }

    private Medico registrarMedico(String nombre, String email, String documento, Especialidad especialidad){
        var medico = new Medico(datosMedico(nombre, email, documento, especialidad));
        em.persist(medico);
        return medico;
    }

    private Paciente registrarPaciente(String nombre, String email, String documento){
        var paciente = new Paciente(datosPaciente(nombre, email, documento));
        em.persist(paciente);
        return paciente;
    }

    private DatosRegistroMedico datosMedico(String nombre, String email, String documento, Especialidad especialidad){
        return new DatosRegistroMedico(
                nombre,
                email,
                "1234567890",
                documento,
                especialidad,
                datosDireccion());
    }

    private DatosRegistroPaciente datosPaciente(String nombre, String email, String docuemnto){
        return new DatosRegistroPaciente(

                nombre,
                email,
                "1234567890",
                docuemnto,
                datosDireccion());
    }

    private DatosDireccion datosDireccion(){
        return new DatosDireccion(
                "Madero",
                "Centro",
                "Acambaro",
                "Guanajuato",
                "38600");
    }
}

//En flyway se pueden insertar los pacientes y medicos
//directamente sobre la database de prueba vollmed_api_test
//Se tendrian que borrar todos los migration files actuales para cambiarlos
//por los que la database de prueba