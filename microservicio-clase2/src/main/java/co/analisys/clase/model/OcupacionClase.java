package co.analisys.clase.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OcupacionClase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String claseId;
    int ocupacionActual;
    LocalDateTime now;

    public OcupacionClase(String claseId, int ocupacionActual, LocalDateTime now) {
        this.claseId = claseId;
        this.ocupacionActual = ocupacionActual;
        this.now = now;
    }
    
}
