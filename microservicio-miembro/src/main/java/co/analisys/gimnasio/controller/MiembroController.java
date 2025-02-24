package co.analisys.gimnasio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.analisys.gimnasio.model.FechaInscripcion;
import co.analisys.gimnasio.model.Miembro;
import co.analisys.gimnasio.service.MiembroService;

@RestController
@RequestMapping("/miembro")
public class MiembroController {
    @Autowired
    private MiembroService circulacionService;

    @GetMapping("/all")
    public List<Miembro> getAllMiembros() {
        return circulacionService.obtenerTodosMiembros();
    }

    @PostMapping("/add")
    public Miembro addMiembro(@RequestParam String nombre, @RequestParam String email) {
        return circulacionService.registrarMiembro(new Miembro(null, nombre,email,new FechaInscripcion()));
    }
}
