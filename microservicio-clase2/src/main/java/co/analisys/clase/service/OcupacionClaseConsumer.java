package co.analisys.clase.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import co.analisys.clase.dto.OcupacionClase;

@Service
public class OcupacionClaseConsumer {
    
    @KafkaListener(topics = "ocupacion-clases", groupId = "monitoreo-grupo")
    public void consumirActualizacionOcupacion(OcupacionClase ocupacion) {
        // Actualizar dashboard en tiempo real
        actualizarDashboard(ocupacion);
    }

    public void actualizarDashboard(OcupacionClase ocupacion) {
        System.out.println("Actualizando dashboard con ocupación de la clase " + ocupacion.getClaseId() + " a " + ocupacion.getOcupacionActual());
    }

}
