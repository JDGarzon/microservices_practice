package co.analisys.entrenador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.analisys.entrenador.model.Entrenador;

public interface EntrenadorRepository extends JpaRepository<Entrenador, Long> {
}
