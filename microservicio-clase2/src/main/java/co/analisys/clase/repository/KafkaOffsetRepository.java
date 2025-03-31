package co.analisys.clase.repository;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class KafkaOffsetRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void init() {
        String sql = "CREATE TABLE IF NOT EXISTS kafka_offsets (" +
                     "id INT AUTO_INCREMENT PRIMARY KEY, " +
                     "topic_name VARCHAR(255) NOT NULL, " +
                     "partition_id INT NOT NULL, " +
                     "offset_value BIGINT NOT NULL, " +
                     "UNIQUE (topic_name, partition_id))";
        jdbcTemplate.execute(sql);
    }


    public void guardarOffset(String topic, int partition, long offset) {
        String sql = "MERGE INTO kafka_offsets (topic_name, partition_id, offset_value) " +
                     "KEY (topic_name, partition_id) " +
                     "VALUES (?, ?, ?)";
        try {
            jdbcTemplate.update(sql, topic, partition, offset);
        } catch (Exception e) {
            throw new RuntimeException("Error guardando offset en la BD", e);
        }
    }

    public Map<TopicPartition, Long> cargarUltimoOffset() {
        String sql = "SELECT topic_name, partition_id, offset_value FROM kafka_offsets";
        return jdbcTemplate.query(sql, rs -> {
            Map<TopicPartition, Long> offsets = new HashMap<>();
            while (rs.next()) {
                offsets.put(new TopicPartition(rs.getString("topic_name"), rs.getInt("partition_id")),
                            rs.getLong("offset_value"));
            }
            return offsets;
        });
    }
}
