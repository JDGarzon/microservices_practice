package co.analisys.clase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.analisys.clase.model.Offset;

public interface OffsetRepository  extends JpaRepository<Offset, Long> {

}
