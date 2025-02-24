package co.analisys.equipo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.analisys.equipo.model.Equipo;
import co.analisys.equipo.model.EquipoId;

public interface EquipoRepository extends JpaRepository<Equipo, EquipoId> {
}
