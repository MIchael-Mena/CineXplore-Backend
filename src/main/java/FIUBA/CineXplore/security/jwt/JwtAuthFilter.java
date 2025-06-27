package FIUBA.CineXplore.security.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final JwtEntryPoint jwtEntryPoint;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            authenticateToken(request);
            filterChain.doFilter(request, response);
        } catch (AuthenticationException ex) {
            SecurityContextHolder.clearContext();
            // Llamar a JwtEntryPoint para mostrar el mensaje personalizado
            jwtEntryPoint.commence(request, response, ex);
        }
    }

    private void authenticateToken(@NonNull HttpServletRequest request) {
        // Si ya hay una autenticación en el contexto, no hacemos nada
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return;
        }

        // Obtenemos el token del header Authorization
        String authHeader = request.getHeader("Authorization");
        String headerPrefix = "Bearer ";
        if (authHeader == null || !authHeader.startsWith(headerPrefix)) {
            return;
        }
        String token = authHeader.substring(headerPrefix.length());

        // Intentamos extraer y verificar los datos del usuario desde el token
        jwtProvider.extractVerifiedUserData(token).ifPresent(userTokenData -> {
            List<SimpleGrantedAuthority> authorities = userTokenData.roles().stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role)) // Prepend "ROLE_" to each role for Spring Security
                    .collect(Collectors.toList());

            var authToken = new UsernamePasswordAuthenticationToken(
                    userTokenData.email(),
                    null,
                    authorities
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        });
    }
}
