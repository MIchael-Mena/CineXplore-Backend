package FIUBA.CineXplore.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {
    private final boolean success;
    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String message;
    private final T data;

    // Constructor adicional para mantener compatibilidad con el código existente
    public ApiResponse(int status, String message, T data) {
        this.success = status >= 200 && status < 300;
        this.status = status;
        this.message = message;
        this.data = data;
    }
}