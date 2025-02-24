package co.analisys.gimnasio.exception;

public class MiembroNoEncontradoException extends RuntimeException {

    public MiembroNoEncontradoException(Long miembroId) {
        super("No se encontró el miembro con ID " + miembroId);
    }
}
