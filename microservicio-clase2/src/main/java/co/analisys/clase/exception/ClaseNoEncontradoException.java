package co.analisys.clase.exception;


public class ClaseNoEncontradoException extends RuntimeException {

    public ClaseNoEncontradoException(Long claseId) {
        super("No se encontró la clase con ID " + claseId);
    }
}
