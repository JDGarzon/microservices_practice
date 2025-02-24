package co.analisys.gimnasio.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Miembro {
    @Id
    @EmbeddedId
    private MiembroId id;
    private String nombre;
    private String email;
    private FechaInscripcion fechaInscripcion;
}
