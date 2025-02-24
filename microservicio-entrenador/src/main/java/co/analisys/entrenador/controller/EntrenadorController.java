package co.analisys.entrenador.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.analisys.entrenador.model.Entrenador;
import co.analisys.entrenador.service.EntrenadorService;

@RestController
@RequestMapping("/entrenador")
public class EntrenadorController {
    @Autowired
    private EntrenadorService entrenadorService;

    @GetMapping("/all")
    public List<Entrenador> obtenerTodosEntrenadores() {
        return entrenadorService.obtenerTodosEntrenadores();
    }

    @PostMapping("/add/{nombre}/{especialidad}")
    public Entrenador registrarEntrenador(@PathVariable String nombre, @PathVariable String especialidad) {
        return entrenadorService.registrarEntrenador(new Entrenador(null,nombre, especialidad));
    }

    @GetMapping("/{id}")
    public Entrenador obtenerEntrenador(@PathVariable Long id){
        return entrenadorService.obtenerEntrenador(id);
    }
}
