package com.voll.api.domain.consulta;

import com.voll.api.domain.consulta.validacionesAgendar.ValidadorAgendaConsultas;
import com.voll.api.domain.consulta.validacionesCancelar.ValidadorCancelacionConsultas;
import com.voll.api.domain.medico.Medico;
import com.voll.api.domain.medico.MedicoRepository;
import com.voll.api.domain.paciente.PacienteRepository;
import com.voll.api.infra.errores.ValidacionDeIntegridad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgendaDeConsultaService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    List<ValidadorAgendaConsultas> validadoresAgenda;

    @Autowired
    List<ValidadorCancelacionConsultas> validadoresCancelacion;

    public DatosDetalleConsulta agendar(DatosAgendarConsulta datosAgendarConsulta){

        if (!pacienteRepository.findById(datosAgendarConsulta.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("Este id de paciente no fue encontrado");
        }
        if (datosAgendarConsulta.idMedico() != null && !medicoRepository.existsById(datosAgendarConsulta.idMedico())){
            throw new ValidacionDeIntegridad("Este id de medico no fue encontrado");
        }

        validadoresAgenda.forEach(v->v.validar(datosAgendarConsulta));

        var paciente = pacienteRepository.findById(datosAgendarConsulta.idPaciente()).get();
        var medico = seleccionarMedico(datosAgendarConsulta);

        if (medico == null){
            throw new ValidacionDeIntegridad("No hay medicos disponibles para esta especialidad");
        }

        var consulta = new Consulta(null, medico, paciente, datosAgendarConsulta.fecha(), Boolean.TRUE, MotivoCancelacion.NO_CANCELADA);
        consultaRepository.save(consulta);

        return new DatosDetalleConsulta(consulta);
    }

    //public 

    private Medico seleccionarMedico(DatosAgendarConsulta datosAgendarConsulta) {
        if(datosAgendarConsulta.idMedico()!=null){
            return medicoRepository.getReferenceById(datosAgendarConsulta.idMedico());
        }
        if(datosAgendarConsulta.especialidad()==null){
            throw new ValidacionDeIntegridad("Debe seleccionarse una especialidad para el medico");
        }

        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datosAgendarConsulta.especialidad(), datosAgendarConsulta.fecha());
    }

    public void cancelar(Consulta consulta, MotivoCancelacion motivoCancelacion) {
        System.out.println(motivoCancelacion);
        if(motivoCancelacion==null){
            System.out.println("Si entro al if de motivoCancelacion == null");
            throw new ValidacionDeIntegridad("Debe seleccionarse un motivo de cancelacion");
        }
        validadoresCancelacion.forEach(v->v.cancelar(consulta));
        System.out.println(motivoCancelacion);
        consulta.eliminarConsulta(motivoCancelacion);
    }
}
