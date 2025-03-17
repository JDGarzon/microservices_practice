package co.analisys.clase.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@Entity
@AllArgsConstructor
public class ResumenEntrenamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String datos;

    public ResumenEntrenamiento() {
        datos = "";
    }


    public ResumenEntrenamiento actualizar(DatosEntrenamiento value) {
        datos += value.getDatos();
        return this;
    }
    
}
