package co.analisys.clase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.analisys.clase.model.Clase;
import co.analisys.clase.repository.ClaseRepository;


@Service
public class ClaseService {
    @Autowired
    private ClaseRepository claseRepository;

    public List<Clase> obtenerTodasClases() {
        return claseRepository.findAll();
    }

    public Clase registrarClase(Clase clase) {
        return claseRepository.save(clase);
    }

}
