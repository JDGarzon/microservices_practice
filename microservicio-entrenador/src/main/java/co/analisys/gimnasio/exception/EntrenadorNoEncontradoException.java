package co.analisys.gimnasio.exception;

import co.analisys.gimnasio.model.EntrenadorId;

public class EntrenadorNoEncontradoException extends RuntimeException {

    public EntrenadorNoEncontradoException(EntrenadorId entrenadorId) {
        super("No se encontró el entrenador con ID " + entrenadorId.getEntrenador_value());
    }
}
