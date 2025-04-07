package co.analisys.gimnasio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.notification.NotificacionDTO;
import co.analisys.gimnasio.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/notificar")
public class NotificacionController {
    @Autowired
    private NotificacionService notificacionService;

    private static final Logger logger = LoggerFactory.getLogger(ClaseController.class);

    @Operation(
        summary = "Enviar notificación",
        description = "Permite enviar una notificación a un usuario específico. " +
                      "El contenido de la notificación se recibe en formato JSON como un objeto NotificacionDTO."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Notificación enviada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "403", description = "Acceso no autorizado")
    })
    @PostMapping("/rb")
    public void enviarNotificacion(@RequestBody NotificacionDTO notificacion) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Usuario autenticado: {}", auth.getName());
        logger.info("Authorities: {}", auth.getAuthorities());
        notificacionService.enviarNotificacion(notificacion);
    }

    @Operation(
        summary = "Enviar notificación",
        description = "Permite enviar una notificación a un usuario específico. " +
                      "El contenido de la notificación se recibe en formato JSON como un objeto NotificacionDTO."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Notificación enviada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
        @ApiResponse(responseCode = "403", description = "Acceso no autorizado")
    })
    @PostMapping
    public void enviarNotificacionKafka(@RequestBody NotificacionDTO notificacion) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Usuario autenticado: {}", auth.getName());
        logger.info("Authorities: {}", auth.getAuthorities());
        notificacionService.enviarNotificacionKafka(notificacion);
    }

   
    
}