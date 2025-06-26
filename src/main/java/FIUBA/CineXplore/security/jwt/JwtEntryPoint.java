package FIUBA.CineXplore.security.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

// En caso de falla de autenticación en el filtro JwtAuthFilter, se redirige aquí
@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

    // logger solo se usa en desarrollo para ver errores en consola
//    private final static Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        logger.error("El usuario no está autenticado");
        this.handleAuthenticationErrorWithApiResponse(request, response, authException.getMessage());
    }

    private void handleAuthenticationErrorWithApiResponse(HttpServletRequest request,
                                                          HttpServletResponse response,
                                                          String errorMessage) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Usar el mensaje específico o uno por defecto
        String message = errorMessage != null ? errorMessage : "Acceso denegado - Se requiere autenticación";

        // Crear JSON manualmente para evitar problemas de serialización
        String jsonResponse = String.format(
                "{\"timestamp\":\"%s\",\"status\":%d,\"message\":\"%s\",\"data\":null}",
                java.time.LocalDateTime.now(),
                HttpServletResponse.SC_UNAUTHORIZED,
                message
        );

        response.getWriter().write(jsonResponse);
    }

}
