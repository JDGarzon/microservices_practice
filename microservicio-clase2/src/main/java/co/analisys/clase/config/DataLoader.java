package co.analisys.clase.config;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.analisys.clase.model.Clase;
import co.analisys.clase.repository.ClaseRepository;

@Configuration
public class DataLoader {
    @Bean
    public CommandLineRunner loadData(ClaseRepository claseRepository) {
        return args -> {
            if (claseRepository.count() == 0) {
                Clase clase1 = new Clase(null, "Yoga Matutino", LocalDateTime.now().plusDays(1), 20, 1L, List.of(101L, 102L));
                Clase clase2 = new Clase(null, "Entrenamiento Funcional", LocalDateTime.now().plusDays(2), 15, 2L, List.of(103L, 104L));
                Clase clase3 = new Clase(null, "Pilates", LocalDateTime.now().plusDays(3), 12, 3L, List.of(105L, 106L));
                
                claseRepository.saveAll(List.of(clase1, clase2, clase3));
                System.out.println("Datos de clases inicializados.");
            }
        };
    }
}
