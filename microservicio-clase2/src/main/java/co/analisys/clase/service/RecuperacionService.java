package co.analisys.clase.service;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.analisys.clase.repository.KafkaOffsetRepository;

@Service
public class RecuperacionService {
    @Autowired
    private KafkaConsumer<String, String> consumer;

    @Autowired
    private KafkaOffsetRepository offsetRepository;

    private volatile boolean running = true;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

        public void iniciarProcesamiento() {
        executor.submit(this::procesarMensajes);
    }

    private void procesarMensajes() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:29092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "monitoreo-grupo");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false"); // Control manual de offset

        try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Arrays.asList("resumen-entrenamiento", "ocupacion-clases", "datos-entrenamiento"), 
                new ConsumerRebalanceListener() {
                    @Override
                    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                        System.out.println("Particiones revocadas: " + partitions);
                    }

                    @Override
                    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                        System.out.println("Particiones asignadas: " + partitions);
                        Map<TopicPartition, Long> ultimoOffsetProcesado = offsetRepository.cargarUltimoOffset();
                        for (TopicPartition partition : partitions) {
                            Long offset = ultimoOffsetProcesado.getOrDefault(partition, -1L);
                            if (offset >= 0) {
                                consumer.seek(partition, offset + 1);
                                System.out.println("Reiniciando desde offset " + (offset + 1) + " en " + partition);
                            }
                        }
                    }
                }
            );

            while (running) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
                for (ConsumerRecord<String, String> record : records) {
                    procesarRecord(record);
                    offsetRepository.guardarOffset(record.topic(), record.partition(), record.offset());
                }
                consumer.commitSync();
            }
        } catch (Exception e) {
            System.err.println("Error en el consumidor de Kafka: " + e.getMessage());
        }
    }

    public void detenerProcesamiento() {
        running = false;
        executor.shutdown();
    }

    private void procesarRecord(ConsumerRecord<String, String> record) {
        System.out.println("Procesando: " + record.value());
    }
}
