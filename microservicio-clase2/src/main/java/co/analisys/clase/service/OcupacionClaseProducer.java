package co.analisys.clase.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import co.analisys.clase.dto.OcupacionClase;

@Service
public class OcupacionClaseProducer {
    @Autowired
    private final KafkaTemplate<String, OcupacionClase> kafkaTemplate;

    public OcupacionClaseProducer(KafkaTemplate<String, OcupacionClase> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void actualizarOcupacion(String claseId, int ocupacionActual) {
        OcupacionClase ocupacion = new OcupacionClase(claseId, ocupacionActual, LocalDateTime.now());
        kafkaTemplate.send("ocupacion-clases", claseId, ocupacion);
    }
}
