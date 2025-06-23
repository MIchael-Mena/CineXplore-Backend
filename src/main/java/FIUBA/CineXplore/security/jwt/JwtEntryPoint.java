package FIUBA.CineXplore.security.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

    // logger solo se usa en desarrollo para ver errores en consola
    private final static Logger logger = LoggerFactory.getLogger(JwtEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        if (response.getStatus() == HttpServletResponse.SC_OK) {
            // Si el usuario no está autenticado y quiere acceder a un recurso protegido
            logger.error("El usuario no está autenticado");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            logger.error("El usuario no está autenticado");
        }
        logger.error("Fail en el método commence");
    }

}
