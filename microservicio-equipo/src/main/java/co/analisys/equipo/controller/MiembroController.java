package co.analisys.equipo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.analisys.equipo.model.Equipo;
import co.analisys.equipo.service.EquipoService;

@RestController
@RequestMapping("/equipo")
public class MiembroController {
    @Autowired
    private EquipoService equipoService;

    @GetMapping("/all")
    public List<Equipo> getAllMiembros() {
        return equipoService.obtenerTodosEquipos();
    }

    @PostMapping("/add")
    public Equipo addMiembro(@RequestParam String nombre, @RequestParam String descripcion,@RequestParam int cantidad) {
        return equipoService.registrarEquipo(new Equipo(null, nombre,descripcion,cantidad));
    }
}
