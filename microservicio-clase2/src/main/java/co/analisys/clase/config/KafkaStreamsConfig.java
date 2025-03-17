package co.analisys.clase.config;

import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.analisys.clase.model.DatosEntrenamiento;
import co.analisys.clase.model.ResumenEntrenamiento;

import java.time.Duration;

@Configuration
public class KafkaStreamsConfig {

    @Bean
    public StreamsBuilder streamsBuilder() {
        return new StreamsBuilder();
    }

    @Bean
    public KStream<String, DatosEntrenamiento> kStream(StreamsBuilder streamsBuilder) {
        KStream<String, DatosEntrenamiento> stream = streamsBuilder.stream("datos-entrenamiento");
        stream.groupByKey()
                .windowedBy(TimeWindows.of(Duration.ofDays(7)))
                .aggregate(
                        () -> new ResumenEntrenamiento(),
                        (key, value, aggregate) -> aggregate.actualizar(value),
                        Materialized.as("resumen-entrenamiento-store"))
                .toStream()
                .to("resumen-entrenamiento");
        return stream;
    }

}
