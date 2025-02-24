package co.analisys.gimnasio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.analisys.gimnasio.model.FechaInscripcion;
import co.analisys.gimnasio.model.Entrenador;
import co.analisys.gimnasio.service.EntrenadorService;

@RestController
@RequestMapping("/entrenador")
public class EntrenadorController {
    @Autowired
    private EntrenadorService entrenadorService;

    @GetMapping("/all")
    public List<Entrenador> obtenerTodosEntrenadores() {
        return entrenadorService.obtenerTodosEntrenadores();
    }

    @PostMapping("/add")
    public Entrenador registrarEntrenador(@RequestParam String nombre, @RequestParam String especialidad) {
        return entrenadorService.obtenerTodosEntrenadores(new Entrenador(null, nombre,especialidad));
    }
}
