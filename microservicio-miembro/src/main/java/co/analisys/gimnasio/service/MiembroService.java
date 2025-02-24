package co.analisys.gimnasio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.analisys.gimnasio.model.Miembro;
import co.analisys.gimnasio.repository.MiembroRepository;

@Service
public class MiembroService {
    @Autowired
    private MiembroRepository miembroRepository;

    public List<Miembro> obtenerTodosMiembros() {
        return miembroRepository.findAll();
    }

    public Miembro registrarMiembro(Miembro miembro) {
        return miembroRepository.save(miembro);
    }

}
