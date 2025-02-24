package co.analisys.equipo.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor (force=true)
public class EquipoId {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long usuario_value;
}
