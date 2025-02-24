package co.analisys.gimnasio.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Embeddable
@Data
@AllArgsConstructor
public class FechaInscripcion {
    private LocalDate fechaInscripcion_value;

    public FechaInscripcion() {
        this.fechaInscripcion_value = LocalDate.now();
    }
}
