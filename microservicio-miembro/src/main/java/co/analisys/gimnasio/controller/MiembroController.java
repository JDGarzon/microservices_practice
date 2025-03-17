package co.analisys.gimnasio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.analisys.gimnasio.model.FechaInscripcion;
import co.analisys.gimnasio.model.Miembro;
import co.analisys.gimnasio.service.MiembroService;

@RestController
@RequestMapping("/miembro")
public class MiembroController {
    @Autowired
    private MiembroService circulacionService;

    @Operation(summary = "Obtener todos los miembros"
            , description = "Obtiene todos los miembros registrados en la base de datos"
            , tags = {"miembro"})
    @GetMapping("/all")
    public List<Miembro> getAllMiembros() {
        return circulacionService.obtenerTodosMiembros();
    }

    @Operation(summary = "Registrar un nuevo miembro"
            , description = "Registra un nuevo miembro en la base de datos"
            , tags = {"miembro"})
    @PostMapping("/add/{nombre}/{email}")
    public Miembro addMiembro(@PathVariable String nombre, @PathVariable String email) {
        return circulacionService.registrarMiembro(new Miembro(null, nombre,email,new FechaInscripcion()));
    }
}
