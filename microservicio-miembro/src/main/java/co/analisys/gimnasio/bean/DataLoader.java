package co.analisys.gimnasio.bean;

import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.analisys.gimnasio.model.FechaInscripcion;
import co.analisys.gimnasio.model.Miembro;
import co.analisys.gimnasio.repository.MiembroRepository;

@Configuration
public class DataLoader{
    @Bean
    public CommandLineRunner initMiembroData(MiembroRepository miembroRepository) {
        return args -> {
            if (miembroRepository.count() == 0) {
                Miembro miembro1 = new Miembro(null, "Juan Pérez", "juan.perez@email.com", new FechaInscripcion(LocalDate.now()));
                Miembro miembro2 = new Miembro(null, "María Gómez", "maria.gomez@email.com", new FechaInscripcion(LocalDate.now().minusDays(10)));
                Miembro miembro3 = new Miembro(null, "Carlos López", "carlos.lopez@email.com", new FechaInscripcion(LocalDate.now().minusMonths(1)));
                
                miembroRepository.saveAll(List.of(miembro1, miembro2, miembro3));
                System.out.println("Datos de miembros precargados en la base de datos.");
            }
        };
    }
}
