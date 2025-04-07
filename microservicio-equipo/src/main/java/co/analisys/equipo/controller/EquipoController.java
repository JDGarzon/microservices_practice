package co.analisys.equipo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.analisys.equipo.model.Equipo;
import co.analisys.equipo.service.EquipoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/equipo")
public class EquipoController {
    @Autowired
    private EquipoService equipoService;

    private static final Logger logger = LoggerFactory.getLogger(EquipoController.class);


    @Operation(summary = "Prueba de servicio"
            , description = "Prueba de servicio para verificar la conexión"
            , tags = {"equipo"}
            , responses = {
                @ApiResponse(responseCode = "200", description = "Servicio funcionando"),
                @ApiResponse(responseCode = "500", description = "Error interno en el servidor")
            })
    @GetMapping
    public String testService(HttpServletRequest request) {
        logger.info("I am {}", request.getRequestURL().toString());
        return request.getRequestURL().toString();
    }

    @Operation(summary = "Obtener todos los equipos"
            , description = "Obtiene todos los equipos registrados en la base de datos"
            , tags = {"equipo"}
            , responses = {
                @ApiResponse(responseCode = "200", description = "Equipos obtenidos exitosamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Equipo.class)))),
                @ApiResponse(responseCode = "500", description = "Error interno en el servidor")
            })
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TRAINER')")
    public List<Equipo> getAllEquipos() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Usuario autenticado: {}", auth.getName());
        logger.info("Authorities: {}", auth.getAuthorities());
        return equipoService.obtenerTodosEquipos();
    }

    @Operation(summary = "Registrar un nuevo equipo"
            , description = "Registra un nuevo equipo en la base de datos"
            , tags = {"equipo"}
            , responses = {
                @ApiResponse(responseCode = "200", description = "Equipo registrado exitosamente", content = @Content(schema = @Schema(implementation = Equipo.class))),
                @ApiResponse(responseCode = "400", description = "Error en los datos del equipo"),
                @ApiResponse(responseCode = "500", description = "Error interno en el servidor")
            })
    @PostMapping("/add/{nombre}/{descripcion}/{cantidad}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TRAINER')")
    public Equipo addEquipo(@PathVariable String nombre, @PathVariable String descripcion,@PathVariable int cantidad) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Usuario autenticado: {}", auth.getName());
        logger.info("Authorities: {}", auth.getAuthorities());
        return equipoService.registrarEquipo(new Equipo(null, nombre,descripcion,cantidad));
    }
}
