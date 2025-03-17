package co.analisys.clase.model;

import java.io.Serializable;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class ResumenEntrenamiento implements Serializable {

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
