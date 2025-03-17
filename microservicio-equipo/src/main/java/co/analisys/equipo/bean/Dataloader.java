package co.analisys.equipo.bean;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.analisys.equipo.model.Equipo;
import co.analisys.equipo.repository.EquipoRepository;

@Configuration
public class Dataloader {
     @Bean
    public CommandLineRunner loadEquipoData(EquipoRepository equipoRepository) {
        return args -> {
            if (equipoRepository.count() == 0) {
                equipoRepository.save(new Equipo(null, "Pesas", "Pesas de gimnasio", 10));
                equipoRepository.save(new Equipo(null, "Bicicletas", "Bicicletas estáticas", 5));
                equipoRepository.save(new Equipo(null, "Mancuernas", "Juego de mancuernas", 20));
                System.out.println("Datos de equipos inicializados.");
            }
        };
    }
}
