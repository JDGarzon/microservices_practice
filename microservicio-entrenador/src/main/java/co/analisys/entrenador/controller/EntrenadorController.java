package co.analisys.entrenador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.analisys.entrenador.model.Entrenador;
import co.analisys.entrenador.service.EntrenadorService;
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
@RequestMapping("/entrenador")
public class EntrenadorController {
    @Autowired
    private EntrenadorService entrenadorService;

    private static final Logger logger = LoggerFactory.getLogger(EntrenadorController.class);

    @Operation(summary = "Obtener todos los entrenadores"
            , description = "Obtiene todos los entrenadores registrados en la base de datos"
            , tags = {"entrenador"}
            , responses = {
                @ApiResponse(responseCode = "200", description = "Entrenadores obtenidos exitosamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Entrenador.class)))),
                @ApiResponse(responseCode = "500", description = "Error interno en el servidor")
            })
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TRAINER','ROLE_MEMBER')")
    public List<Entrenador> obtenerTodosEntrenadores() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Usuario autenticado: {}", auth.getName());
        logger.info("Authorities: {}", auth.getAuthorities());
        return entrenadorService.obtenerTodosEntrenadores();
    }

    @Operation(summary = "Registrar un nuevo entrenador"
            , description = "Registra un nuevo entrenador en la base de datos"
            , tags = {"entrenador"}
            , responses = {
                @ApiResponse(responseCode = "200", description = "Entrenador registrado exitosamente", content = @Content(schema = @Schema(implementation = Entrenador.class))),
                @ApiResponse(responseCode = "400", description = "Error en los datos del entrenador"),
                @ApiResponse(responseCode = "500", description = "Error interno en el servidor")
            })
    @PostMapping("/add/{nombre}/{especialidad}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Entrenador registrarEntrenador(@PathVariable String nombre, @PathVariable String especialidad) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Usuario autenticado: {}", auth.getName());
        logger.info("Authorities: {}", auth.getAuthorities());
        return entrenadorService.registrarEntrenador(new Entrenador(null,nombre, especialidad));
    }

    @Operation(summary = "Obtener un entrenador"
            , description = "Obtiene un entrenador registrado en la base de datos"
            , tags = {"entrenador"}
            , responses = {
                @ApiResponse(responseCode = "200", description = "Entrenador obtenido exitosamente", content = @Content(schema = @Schema(implementation = Entrenador.class))),
                @ApiResponse(responseCode = "400", description = "Error en los datos del entrenador"),
                @ApiResponse(responseCode = "500", description = "Error interno en el servidor")
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TRAINER','ROLE_MEMBER')")
    public Entrenador obtenerEntrenador(@PathVariable Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Usuario autenticado: {}", auth.getName());
        logger.info("Authorities: {}", auth.getAuthorities());
        return entrenadorService.obtenerEntrenador(id);
    }
}
