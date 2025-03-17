package co.analisys.clase.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import co.analisys.clase.model.OcupacionClase;

@Service
public class OcupacionClaseProducer {
    @Autowired
    private KafkaTemplate<String, OcupacionClase> kafkaTemplate;

    public void actualizarOcupacion(String claseId, int ocupacionActual) {
        OcupacionClase ocupacion = new OcupacionClase(claseId, ocupacionActual, LocalDateTime.now());
        kafkaTemplate.send("ocupacion-clases", claseId, ocupacion);
    }
}
