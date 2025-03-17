package co.analisys.clase.dto;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionHorarioDTO implements Serializable {
    private List<Long> usuariosId;
    private String mensaje;

    public List<Long> getUsuariosId() {
        return this.usuariosId;
    }

    public void setUsuariosId(List<Long> usuariosId) {
        this.usuariosId = usuariosId;
    }

    public String getMensaje() {
        return this.mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return "NotificacionHorarioDTO{" +
            "usuariosId=" + usuariosId +
            ", mensaje='" + mensaje + '\'' +
            '}';
    }
}