package co.analisys.clase.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.analisys.clase.model.Clase;
import co.analisys.clase.repository.ClaseRepository;
import co.analisys.clase.exception.ClaseNoEncontradoException;
import java.util.ArrayList;


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

    public Clase obtenerClase(Long id) {
        return claseRepository.findById(id).orElse(null);

    }

    public Clase registrarMiembroAClase(Long idClase, Long idMiembro) {
        Clase clase = claseRepository.findById(idClase)
        .orElseThrow(() -> new ClaseNoEncontradoException(idClase));

        if (clase.getMiembros() == null) {
            clase.setMiembros(new ArrayList<>());
        }

        clase.getMiembros().add(idMiembro);
        return claseRepository.save(clase);
    }

}
