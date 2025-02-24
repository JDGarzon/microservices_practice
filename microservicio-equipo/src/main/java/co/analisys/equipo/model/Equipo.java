package co.analisys.equipo.model;


import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
public class Equipo {
    @EmbeddedId
    private EquipoId id;
    private String nombre;
    private String descripcion;
    private int cantidad;
}
