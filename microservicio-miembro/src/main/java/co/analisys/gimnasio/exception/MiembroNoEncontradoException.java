package co.analisys.gimnasio.exception;

import co.analisys.gimnasio.model.MiembroId;

public class MiembroNoEncontradoException extends RuntimeException {

    public MiembroNoEncontradoException(MiembroId miembroId) {
        super("No se encontró el miembro con ID " + miembroId.getUsuario_value());
    }
}
