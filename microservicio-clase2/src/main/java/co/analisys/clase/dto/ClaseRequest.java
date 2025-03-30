package co.analisys.clase.dto;

import java.time.LocalDateTime;

public class ClaseRequest {
    private long claseId;
    private LocalDateTime time;

    // Getters y Setters
    public long getClaseId() {
        return claseId;
    }

    public void setClaseId(long claseId) {
        this.claseId = claseId;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}