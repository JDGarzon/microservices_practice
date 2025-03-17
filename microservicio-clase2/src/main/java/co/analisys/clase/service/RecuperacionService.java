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

import co.analisys.clase.model.Message_offset;
import co.analisys.clase.repository.OffsetRepository;

@Service
public class RecuperacionService {
    @Autowired
    private KafkaConsumer<String, String> consumer;
    @Autowired
    private OffsetRepository offsetRepository;

    public void iniciarProcesamiento() {
        consumer.subscribe(Arrays.asList("datos-entrenamiento", "ocupacion-clases"));
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
        offsetRepository.findAll().forEach(offset -> {
            consumer.seek(new TopicPartition(offset.getTopic(), offset.getPartition()), offset.getOffset());
        });
        Map<TopicPartition, Long> ultimoOffsetProcesado = new HashMap<>();
        consumer.assignment().forEach(topicPartition -> {
            ultimoOffsetProcesado.put(topicPartition, consumer.position(topicPartition));
        });
        return ultimoOffsetProcesado;
    }

    private void procesarRecord(ConsumerRecord<String, String> record) {
        // Implement your record processing logic here
        System.out.println("Processing record: " + record.value());
    }

    private void guardarOffset(String topic, int partition, long offset) {
        Message_offset offsetO = new Message_offset(topic, partition, offset);
        offsetRepository.save(offsetO);
    }
    
}
