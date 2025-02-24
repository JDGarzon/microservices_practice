package co.analisys.gimnasio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.analisys.gimnasio.model.Entrenador;
import co.analisys.gimnasio.model.EntrenadorId;

public interface EntrenadorRepository extends JpaRepository<Entrenador, EntrenadorId> {
}
