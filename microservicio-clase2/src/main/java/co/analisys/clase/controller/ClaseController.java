package co.analisys.clase.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.analisys.clase.model.Clase;
import co.analisys.clase.service.ClaseService;

@RestController
@RequestMapping("/clase")
public class ClaseController {
    @Autowired
    private ClaseService claseService;

    @Operation(summary = "Obtener todas las clases"
            , description = "Obtiene todas las clases registradas en la base de datos"
            , tags = {"clase"})
    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Clase> obtenerTodasClases() {
        return claseService.obtenerTodasClases();
    }

    @Operation(summary = "Registrar una nueva clase"
            , description = "Registra una nueva clase en la base de datos"
            , tags = {"clase"})
    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Clase registrarClase(@RequestBody Clase nuevaClase) {
        return claseService.registrarClase(nuevaClase);
    }

}
