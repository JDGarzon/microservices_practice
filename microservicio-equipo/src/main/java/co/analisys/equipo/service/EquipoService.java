package co.analisys.equipo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.analisys.equipo.model.Equipo;
import co.analisys.equipo.repository.EquipoRepository;

@Service
public class EquipoService {
    @Autowired
    private EquipoRepository equipoRepository;

    public List<Equipo> obtenerTodosEquipos() {
        return equipoRepository.findAll();
    }

    public Equipo registrarEquipo(Equipo equipo) {
        return equipoRepository.save(equipo);
    }

}
