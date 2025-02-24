package co.analisys.equipo.exception;

import co.analisys.equipo.model.EquipoId;

public class MiembroNoEncontradoException extends RuntimeException {

    public MiembroNoEncontradoException(EquipoId equipoId) {
        super("No se encontró el equipo con ID " + equipoId.getUsuario_value());
    }
}
