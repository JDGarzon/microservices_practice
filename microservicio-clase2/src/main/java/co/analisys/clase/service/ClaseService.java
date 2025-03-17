package co.analisys.clase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.analisys.clase.model.Clase;
import co.analisys.clase.repository.ClaseRepository;
import co.analisys.clase.exception.ClaseNoEncontradoException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import co.analisys.clase.dto.NotificacionDTO;
import co.analisys.clase.dto.NotificacionHorarioDTO;
import co.analisys.clase.config.RabbitMQConfig;

@Service
public class ClaseService {
    @Autowired
    private ClaseRepository claseRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public List<Clase> obtenerTodasClases() {
        return claseRepository.findAll();
    }

    public Clase registrarClase(Clase clase) {
        return claseRepository.save(clase);
    }

    public Clase obtenerClase(Long id) {
        return claseRepository.findById(id).orElse(null);

    }

    public Clase registrarMiembroAClase(Long idClase, Long idMiembro) {
        Clase clase = claseRepository.findById(idClase)
        .orElseThrow(() -> new ClaseNoEncontradoException(idClase));

        if (clase.getMiembros() == null) {
            clase.setMiembros(new ArrayList<>());
        }

        clase.getMiembros().add(idMiembro);
        clase = claseRepository.save(clase);
        NotificacionDTO notificacion = new NotificacionDTO(idMiembro.toString(), "Te has inscrito a la clase " + clase.getNombre());
        rabbitTemplate.convertAndSend("notificacion.exchange", "notificacion.routingkey", notificacion);
        return clase;
    }

    public Clase cambiarHorarioClase(Long idClase, LocalDateTime horario) {
        Clase clase = claseRepository.findById(idClase)
        .orElseThrow(() -> new ClaseNoEncontradoException(idClase));

        clase.setHorario(horario);
        clase = claseRepository.save(clase);
        NotificacionHorarioDTO notificacion = new NotificacionHorarioDTO(clase.getMiembros(), "El horario de la clase " + clase.getNombre() + " ha cambiado a " + horario);
        notificarCambioHorario(notificacion);
        return clase;
    }

    public void notificarCambioHorario(NotificacionHorarioDTO notificacion) {
        rabbitTemplate.convertAndSend("notificacion.exchange", RabbitMQConfig.ROUTING_KEY_HORARIO, notificacion);
    }

}
