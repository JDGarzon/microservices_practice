package co.analisys.gimnasio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.analisys.gimnasio.model.Entrenador;
import co.analisys.gimnasio.repository.EntrenadorRepository;
import co.analisys.gimnasio.model.EntrenadorId;


@Service
public class EntrenadorService {
    @Autowired
    private EntrenadorRepository entrenadorRepository;

    public List<Entrenador> obtenerTodosEntrenadores() {
        return entrenadorRepository.findAll();
    }

    public Entrenador registrarEntrenador(Entrenador entrenador) {
        return entrenadorRepository.save(entrenador);
    }

    public Entrenador obtenerEntrenador(EntrenadorId id) {
        return entrenadorRepository.findById(id).orElse(null);

    }

}
