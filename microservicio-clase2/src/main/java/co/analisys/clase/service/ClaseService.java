package co.analisys.clase.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.example.demo.entrenamiento.DatosEntrenamiento;
import com.example.demo.notification.NotificacionDTO;

import co.analisys.clase.config.RabbitMQConfig;
import co.analisys.clase.dto.NotificacionHorarioDTO;
import co.analisys.clase.exception.ClaseNoEncontradoException;
import co.analisys.clase.model.Clase;
import co.analisys.clase.repository.ClaseRepository;
import co.analisys.clase.repository.KafkaOffsetRepository;

@Service 
public class ClaseService {
    @Autowired
    private ClaseRepository claseRepository;

    @Autowired
    private KafkaTemplate<String, NotificacionDTO> kafkaTemplate;

    @Autowired
    private KafkaTemplate<String, DatosEntrenamiento> kafkaTemplateEntrenamiento;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private KafkaOffsetRepository offsetRepository;

    public List<Clase> obtenerTodasClases() {
        return claseRepository.findAll();
    }

    public Clase registrarClase(Clase clase) {
        return claseRepository.save(clase);
    }

    public Clase obtenerClase(Long id) {
        return claseRepository.findById(id).orElse(null);

    }

    public void enviarDatosEntrenamiento(DatosEntrenamiento datos) {
        CompletableFuture<SendResult<String,DatosEntrenamiento>> future =kafkaTemplateEntrenamiento.send("datos-entrenamiento", ""+datos.getIdUsuario(), datos);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                long offset = result.getRecordMetadata().offset();
                int partition = result.getRecordMetadata().partition();
                offsetRepository.guardarOffset(result.getRecordMetadata().topic(), partition, offset);
                System.out.println("Mensaje enviado con éxito. Offset: " + offset + ", Partición: " + partition);
            } else {
                System.err.println("Error enviando el mensaje: " + ex.getMessage());
            }
        });
    }

    public void probarKafka() {
        System.out.println("Enviando mensaje a Kafka...");
        NotificacionDTO notificacion = new NotificacionDTO("1", "Hola desde Kafka");
        CompletableFuture<SendResult<String,NotificacionDTO>> future = kafkaTemplate.send("ocupacion-clases", notificacion);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                long offset = result.getRecordMetadata().offset();
                int partition = result.getRecordMetadata().partition();
                offsetRepository.guardarOffset(result.getRecordMetadata().topic(), partition, offset);
                System.out.println("Mensaje enviado con éxito. Offset: " + offset + ", Partición: " + partition);
            } else {
                System.err.println("Error enviando el mensaje: " + ex.getMessage());
            }
        });
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
        NotificacionDTO notificacionKafka = new NotificacionDTO(idMiembro.toString(), "Se ha incrito un nuevo miembro a la clase " + clase.getNombre() + "\n" 
                + "Miembros actuales: " + clase.getMiembros().toString());
        

        CompletableFuture<SendResult<String,NotificacionDTO>> future = kafkaTemplate.send("ocupacion-clases", notificacionKafka);

        future.whenComplete((result, ex) -> {
            if (ex == null) {
                long offset = result.getRecordMetadata().offset();
                int partition = result.getRecordMetadata().partition();
                offsetRepository.guardarOffset(result.getRecordMetadata().topic(), partition, offset);
                System.out.println("Mensaje enviado con éxito. Offset: " + offset + ", Partición: " + partition);
            } else {
                System.err.println("Error enviando el mensaje: " + ex.getMessage());
            }
        });
            
        return clase;
    }

    public void hacerPago(){
        rabbitTemplate.convertAndSend("notificacion.exchange", "pagos", "Pago realizado");
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
