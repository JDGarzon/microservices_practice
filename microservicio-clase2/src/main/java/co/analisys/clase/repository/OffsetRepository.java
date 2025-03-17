package co.analisys.clase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.analisys.clase.model.Message_offset;

public interface OffsetRepository  extends JpaRepository<Message_offset, Long> {

}
