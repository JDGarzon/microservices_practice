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

@RestController
@RequestMapping("/equipo")
public class MiembroController {
    @Autowired
    private EquipoService equipoService;

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Equipo> getAllMiembros() {
        return equipoService.obtenerTodosEquipos();
    }

    @PostMapping("/add/{nombre}/{descripcion}/{cantidad}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Equipo addMiembro(@PathVariable String nombre, @PathVariable String descripcion,@PathVariable int cantidad) {
        return equipoService.registrarEquipo(new Equipo(null, nombre,descripcion,cantidad));
    }
}
