package co.analisys.equipo.exception;

public class MiembroNoEncontradoException extends RuntimeException {

    public MiembroNoEncontradoException(Long equipoId) {
        super("No se encontró el equipo con ID " + equipoId);
    }
}
