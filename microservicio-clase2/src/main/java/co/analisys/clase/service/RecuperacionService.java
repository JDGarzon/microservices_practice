package co.analisys.clase.service;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecuperacionService {
    @Autowired
    private KafkaConsumer<String, String> consumer;

    public void iniciarProcesamiento() {
        consumer.subscribe(Arrays.asList("topic-a", "topic-b"));
        // Cargar último offset procesado desde una base de datos
        Map<TopicPartition, Long> ultimoOffsetProcesado = cargarUltimoOffset();
        for (Map.Entry<TopicPartition, Long> entry : ultimoOffsetProcesado.entrySet()) {
            consumer.seek(entry.getKey(), entry.getValue());
        }
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                procesarRecord(record);
                guardarOffset(record.topic(), record.partition(), record.offset());
            }
        }
    }

    private Map<TopicPartition, Long> cargarUltimoOffset() {
        // Simulate loading the last processed offset from a database
        Map<TopicPartition, Long> offsets = new HashMap<>();
        offsets.put(new TopicPartition("topic-a", 0), 0L);
        offsets.put(new TopicPartition("topic-b", 0), 0L);
        return offsets;
    }

    private void procesarRecord(ConsumerRecord<String, String> record) {
        // Implement your record processing logic here
        System.out.println("Processing record: " + record.value());
    }

    private void guardarOffset(String topic, int partition, long offset) {
        // Guardar el offset en una base de datos transaccional
        System.out.println("Guardando offset " + offset + " para el topic " + topic + " y partición " + partition);
        
    }

    



}
