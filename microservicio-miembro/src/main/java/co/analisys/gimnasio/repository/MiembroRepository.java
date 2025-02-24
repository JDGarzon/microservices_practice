package co.analisys.gimnasio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.analisys.gimnasio.model.Miembro;
import co.analisys.gimnasio.model.MiembroId;

public interface MiembroRepository extends JpaRepository<Miembro, MiembroId> {
}
