package co.analisys.clase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entrenamiento.DatosEntrenamiento;

import co.analisys.clase.dto.ClaseRequest;
import co.analisys.clase.model.Clase;
import co.analisys.clase.service.ClaseService;
import co.analisys.clase.service.RecuperacionService;
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
@RequestMapping("/clase")
public class ClaseController {
    @Autowired
    private ClaseService claseService;

    @Autowired
    private RecuperacionService recuperacionService;

    private static final Logger logger = LoggerFactory.getLogger(ClaseController.class);


    @Operation(summary = "Obtener todas las clases"
            , description = "Obtiene todas las clases registradas en la base de datos"
            , tags = {"clase"}
            , responses = {
                @ApiResponse(responseCode = "200", description = "Clases obtenidas exitosamente", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Clase.class)))),
                @ApiResponse(responseCode = "500", description = "Error interno en el servidor")
            })
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TRAINER','ROLE_MEMBER')")
    public List<Clase> obtenerTodasClases() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Usuario autenticado: {}", auth.getName());
        logger.info("Authorities: {}", auth.getAuthorities());
        return claseService.obtenerTodasClases();
    }

    @GetMapping("/kafka")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TRAINER','ROLE_MEMBER')")
    public boolean probarKafka() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Usuario autenticado: {}", auth.getName());
        logger.info("Authorities: {}", auth.getAuthorities());
        claseService.probarKafka();
        return true;
    }



    @Operation(summary = "Registrar una nueva clase"
            , description = "Registra una nueva clase en la base de datos"
            , tags = {"clase"}
            , responses = {
                @ApiResponse(responseCode = "200", description = "Clase registrada exitosamente", content = @Content(schema = @Schema(implementation = Clase.class))),
                @ApiResponse(responseCode = "400", description = "Error en los datos de la clase"),
                @ApiResponse(responseCode = "500", description = "Error interno en el servidor")
            })
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Clase registrarClase(@RequestBody Clase nuevaClase) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Usuario autenticado: {}", auth.getName());
        logger.info("Authorities: {}", auth.getAuthorities());
        return claseService.registrarClase(nuevaClase);
    }

    @Operation(summary = "Obtener una clase"
            , description = "Obtiene una clase registrada en la base de datos"
            , tags = {"clase"}
            , responses = {
                @ApiResponse(responseCode = "200", description = "Clase obtenida exitosamente", content = @Content(schema = @Schema(implementation = Clase.class))),
                @ApiResponse(responseCode = "404", description = "Clase no encontrada"),
                @ApiResponse(responseCode = "500", description = "Error interno en el servidor")
            })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TRAINER','ROLE_MEMBER')")
    public Clase obtenerClase(@PathVariable Long id){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Usuario autenticado: {}", auth.getName());
        logger.info("Authorities: {}", auth.getAuthorities());
        return claseService.obtenerClase(id);
    }

    @PostMapping("/add/{idClase}/{idMiembro}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TRAINER','ROLE_MEMBER')")
    public Clase registrarMiembroAClase(@PathVariable long idClase, @PathVariable long idMiembro) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Usuario autenticado: {}", auth.getName());
        logger.info("Authorities: {}", auth.getAuthorities());
        return claseService.registrarMiembroAClase(idClase, idMiembro);
    }

     @PostMapping("/horario")
    public Clase actualizarHorario(@RequestBody ClaseRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Usuario autenticado: {}", auth.getName());
        logger.info("Authorities: {}", auth.getAuthorities());
        return claseService.cambiarHorarioClase(request.getClaseId(), request.getTime());
    }

    @PostMapping("/entrenamiento")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TRAINER','ROLE_MEMBER')")
    public void enviarDatosEntrenamiento(@RequestBody DatosEntrenamiento datos) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Usuario autenticado: {}", auth.getName());
        logger.info("Authorities: {}", auth.getAuthorities());
        claseService.enviarDatosEntrenamiento(datos);

    }

    @PatchMapping("/recuperar")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TRAINER','ROLE_MEMBER')")
    public void recuperarUltimo() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logger.info("Usuario autenticado: {}", auth.getName());
        logger.info("Authorities: {}", auth.getAuthorities());
        recuperacionService.iniciarProcesamiento();
    }
}
