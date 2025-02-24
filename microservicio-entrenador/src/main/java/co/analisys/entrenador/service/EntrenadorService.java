package co.analisys.entrenador.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.analisys.entrenador.model.Entrenador;
import co.analisys.entrenador.repository.EntrenadorRepository;


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

    public Entrenador obtenerEntrenador(Long id) {
        return entrenadorRepository.findById(id).orElse(null);

    }

}
