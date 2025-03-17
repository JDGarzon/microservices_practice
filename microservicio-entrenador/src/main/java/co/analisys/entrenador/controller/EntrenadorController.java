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

@RestController
@RequestMapping("/entrenador")
public class EntrenadorController {
    @Autowired
    private EntrenadorService entrenadorService;

    @Operation(summary = "Obtener todos los entrenadores"
            , description = "Obtiene todos los entrenadores registrados en la base de datos"
            , tags = {"entrenador"})
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TRAINER','ROLE_MEMBER')")
    public List<Entrenador> obtenerTodosEntrenadores() {
        return entrenadorService.obtenerTodosEntrenadores();
    }

    @Operation(summary = "Registrar un nuevo entrenador"
            , description = "Registra un nuevo entrenador en la base de datos"
            , tags = {"entrenador"})
    @PostMapping("/add/{nombre}/{especialidad}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Entrenador registrarEntrenador(@PathVariable String nombre, @PathVariable String especialidad) {
        return entrenadorService.registrarEntrenador(new Entrenador(null,nombre, especialidad));
    }

    @Operation(summary = "Obtener un entrenador"
            , description = "Obtiene un entrenador registrado en la base de datos"
            , tags = {"entrenador"})
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TRAINER','ROLE_MEMBER')")
    public Entrenador obtenerEntrenador(@PathVariable Long id){
        return entrenadorService.obtenerEntrenador(id);
    }
}
