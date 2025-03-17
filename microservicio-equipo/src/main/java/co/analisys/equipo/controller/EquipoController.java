package co.analisys.equipo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.analisys.equipo.model.Equipo;
import co.analisys.equipo.service.EquipoService;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/equipo")
public class EquipoController {
    @Autowired
    private EquipoService equipoService;

    @Operation(summary = "Obtener todos los equipos"
            , description = "Obtiene todos los equipos registrados en la base de datos"
            , tags = {"equipo"})
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TRAINER')")
    public List<Equipo> getAllEquipos() {
        return equipoService.obtenerTodosEquipos();
    }

    @Operation(summary = "Registrar un nuevo equipo"
            , description = "Registra un nuevo equipo en la base de datos"
            , tags = {"equipo"})
    @PostMapping("/add/{nombre}/{descripcion}/{cantidad}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_TRAINER')")
    public Equipo addEquipo(@PathVariable String nombre, @PathVariable String descripcion,@PathVariable int cantidad) {
        return equipoService.registrarEquipo(new Equipo(null, nombre,descripcion,cantidad));
    }
}
