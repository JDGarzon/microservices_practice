package co.analisys.gimnasio.service;

import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.demo.notification.NotificacionDTO;

import co.analisys.gimnasio.dto.NotificacionHorarioDTO;

@Service
public class NotificacionService {

    @KafkaListener(topics = "ocupacion-clases",  groupId = "monitoreo-grupo")
    public void enviarNotificacionKafka(NotificacionDTO notificacion) {
        System.out.println("KAFKA: Usuario: " + notificacion.getUsuarioId() + "Mensaje: "+ notificacion.getMensaje());
    }

    @KafkaListener(topics = "resumen-entrenamiento", groupId = "monitoreo-grupo")
    public void consumirResumen(ConsumerRecord<String, String> record) {
        System.out.println("📥 Recibido resumen en trenamiento: " + record.value());
    }

    @RabbitListener(queues = "notificacion.queue")
    public void recibirNotificacion(NotificacionDTO notificacion) {
        enviarNotificacion(notificacion);
    }

    public void enviarNotificacion(NotificacionDTO notificacion) {
        // Simular envío de notificación
        System.out.println("Notificación enviada a " + notificacion.getUsuarioId() + ": " + notificacion.getMensaje());
    }

    @RabbitListener(queues = "horario.queue")
    public void recibirHorario(NotificacionHorarioDTO notificacionHorario) {
        enviarNotificacionHorario(notificacionHorario);
    }

    public void enviarNotificacionHorario(NotificacionHorarioDTO notificacionHorario) {
        List<Long> usuariosId = notificacionHorario.getUsuariosId();
        for (Long usuarioId : usuariosId) {
            // Simular envío de notificación
            System.out.println("Notificación enviada a " + usuarioId + ": " + notificacionHorario.getMensaje());
        }
    }

    @RabbitListener(queues = "pagos-dlq")
    public void procesarNotificacionPagoFallida() {
        System.out.println("Procesando notificación de pago fallida...");
    }

    @RabbitListener(queues = "pagos-queue")
    public void procesarNotificacionPago() {
        try {
            // Simular procesamiento de notificación de pago
            System.out.println("Procesando notificación de pago...");
            Thread.sleep(5000);
            if (!procesoExitoso()) {
                throw new RuntimeException("Error en el proceso de notificación de pago");
            }

            System.out.println("Notificación de pago procesada exitosamente");
        } catch (Exception e) {
            System.err.println("Error en el proceso de notificación de pago, enviando a DLQ: " + e.getMessage());
            throw new RuntimeException("Error en el proceso de notificación de pago");
        }
    }

    private boolean procesoExitoso() {
        return Math.random() < 0.5;
    }

    @RabbitListener(queues = "pagos-dql")
    public void manejarPagosFallidos() {
        System.out.println("Manejando pagos fallidos desde DLQ...");
    }
}
