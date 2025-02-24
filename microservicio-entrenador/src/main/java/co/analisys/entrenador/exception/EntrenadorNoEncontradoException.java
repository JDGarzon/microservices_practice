package co.analisys.entrenador.exception;


public class EntrenadorNoEncontradoException extends RuntimeException {

    public EntrenadorNoEncontradoException(Long entrenadorId) {
        super("No se encontró el entrenador con ID " + entrenadorId);
    }
}
