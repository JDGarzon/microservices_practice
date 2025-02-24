package co.analisys.gimnasio.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor (force=true)
public class EntrenadorId {
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final Long entrenador_value;
}
