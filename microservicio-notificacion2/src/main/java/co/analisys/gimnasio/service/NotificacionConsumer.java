package co.analisys.gimnasio.service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.analisys.gimnasio.dto.NotificacionDTO;
import co.analisys.gimnasio.dto.NotificacionHorarioDTO;

@Service
public class NotificacionConsumer {
    @Autowired
    private NotificacionService notificacionService;

    @RabbitListener(queues = "notificacion.queue")
    public void recibirNotificacion(NotificacionDTO notificacion) {
        notificacionService.enviarNotificacion(notificacion);
    }

    @RabbitListener(queues = "horario.queue")
    public void recibirHorario(NotificacionHorarioDTO notificacionHorario) {
        notificacionService.enviarNotificacionHorario(notificacionHorario);
    }

    @RabbitListener(queues = "pagos-queue")
    public void recibirPago() {
        notificacionService.procesarNotificacionPago();
    }

    @RabbitListener(queues = "pagos-dlq")
    public void manejarPagosFallidos() {
        notificacionService.procesarNotificacionPagoFallida();
    }
}