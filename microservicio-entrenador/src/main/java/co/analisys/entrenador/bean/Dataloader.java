package co.analisys.entrenador.bean;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.analisys.entrenador.model.Entrenador;
import co.analisys.entrenador.repository.EntrenadorRepository;

@Configuration
public class Dataloader {
    @Bean
    public CommandLineRunner loadData(EntrenadorRepository entrenadorRepository) {
        return args -> {
            if (entrenadorRepository.count() == 0) { // Evitar duplicados en reinicios
                List<Entrenador> entrenadores = List.of(
                        new Entrenador(null, "Juan Pérez", "Fuerza"),
                        new Entrenador(null, "María Gómez", "Resistencia"),
                        new Entrenador(null, "Carlos López", "Velocidad")
                );
                entrenadorRepository.saveAll(entrenadores);
                System.out.println("Entrenadores precargados en la base de datos");
            }
        };
    }
}
