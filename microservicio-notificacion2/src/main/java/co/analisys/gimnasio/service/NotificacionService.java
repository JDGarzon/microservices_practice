package co.analisys.gimnasio.service;

import co.analisys.gimnasio.dto.NotificacionDTO;
import org.springframework.stereotype.Service;
import java.util.List;
import co.analisys.gimnasio.dto.NotificacionHorarioDTO;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.listener.ListenerExecutionFailedException;

@Service
public class NotificacionService {

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
            throw new ListenerExecutionFailedException("Error en el proceso de notificación de pago", e);
        }

    private boolean procesoExitoso() {
        return Math.random() < 0.5;
    }

    @RabbitListener(queues = "pagos-dql")
    public void manejarPagosFallidos() {
        System.out.println("Manejando pagos fallidos desde DLQ...");
    }
}