package co.analisys.gimnasio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.analisys.gimnasio.model.FechaInscripcion;
import co.analisys.gimnasio.model.Miembro;
import co.analisys.gimnasio.service.MiembroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

@RestController
@RequestMapping("/miembro")
public class MiembroController {
    @Autowired
    private MiembroService circulacionService;

    private static final Logger logger = LoggerFactory.getLogger(MiembroController.class);

    @Operation(summary = "Obtener todos los miembros"
            , description = "Obtiene todos los miembros registrados en la base de datos"
            , tags = {"miembro"}
            , responses = {
                @ApiResponse(responseCode = "200", description = "Miembros obtenidos exitosamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Miembro.class)))),
                @ApiResponse(responseCode = "500", description = "Error interno en el servidor")
            })
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TRAINER')")
    public List<Miembro> getAllMiembros() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Usuario autenticado: {}", auth.getName());
        logger.info("Authorities: {}", auth.getAuthorities());
        return circulacionService.obtenerTodosMiembros();
    }

    @Operation(summary = "Registrar un nuevo miembro"
            , description = "Registra un nuevo miembro en la base de datos"
            , tags = {"miembro"}
            , responses = {
                @ApiResponse(responseCode = "200", description = "Miembro registrado exitosamente", content = @Content(schema = @Schema(implementation = Miembro.class))),
                @ApiResponse(responseCode = "400", description = "Error en los datos del miembro"),
                @ApiResponse(responseCode = "500", description = "Error interno en el servidor")
            })
    @PostMapping("/add/{nombre}/{email}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TRAINER','ROLE_MEMBER')")
    public Miembro addMiembro(@PathVariable String nombre, @PathVariable String email) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Usuario autenticado: {}", auth.getName());
        logger.info("Authorities: {}", auth.getAuthorities());
        return circulacionService.registrarMiembro(new Miembro(null, nombre,email,new FechaInscripcion()));
    }
}
