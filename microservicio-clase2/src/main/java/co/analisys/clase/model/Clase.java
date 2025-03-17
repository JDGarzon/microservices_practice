package co.analisys.clase.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.ElementCollection;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Clase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private LocalDateTime horario;
    private int capacidadMaxima;
    private Long entrenadorId;
    
    @ElementCollection
    private List<Long> miembros = new ArrayList<>();

    public List<Long> getMiembros() {
        return this.miembros;
    }

    public void setMiembros(List<Long> miembros) {
        this.miembros = miembros;
    }
}
