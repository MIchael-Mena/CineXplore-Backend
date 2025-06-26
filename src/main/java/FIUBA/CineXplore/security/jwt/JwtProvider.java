package FIUBA.CineXplore.security.jwt;

import FIUBA.CineXplore.security.model.UserCredentials;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JwtProvider {

    private final String secret;
    private final Long expiration;

    public JwtProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") Long expiration
    ) {
        this.secret = secret;
        this.expiration = expiration;
    }

    // Crea el token usando el email y los roles del usuario
    public String createToken(UserCredentials user) {
        List<String> roles = user.getRoles().stream()
                .map(role -> role.getRoleName().name())
                .collect(Collectors.toList());

        return Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration * 1000)) // Expira en 'expiration' segundos
                .claim("roles", roles)
                .signWith(getSigningKey(), Jwts.SIG.HS256) // Usar clave como mínimo 32 caracteres con SHA-256
                .compact();
    }

    // Extrae email y roles del token
    public Optional<UserTokenData> extractVerifiedUserData(String token) {
        try {
            // Verifica el token y extrae los claims
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

            // Extrae el email y los roles del token
            String email = claims.getSubject();
            @SuppressWarnings("unchecked")
            List<String> roles = claims.get("roles", List.class);

            // Si ambos valores son no nulos, devuelve un UserTokenData
            if (email != null && roles != null) {
                return Optional.of(new UserTokenData(email, roles));
            }
        } catch (io.jsonwebtoken.MalformedJwtException e) {
            throw new RuntimeException("El token proporcionado no es válido o está mal formado");
        } catch (io.jsonwebtoken.UnsupportedJwtException e) {
            throw new RuntimeException("El token no es compatible");
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("El token está vacío");
        } catch (io.jsonwebtoken.security.SignatureException e) {
            throw new RuntimeException("Fallo en la firma del token");
        } catch (io.jsonwebtoken.ExpiredJwtException e) {
            throw new RuntimeException("El token ha expirado");
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el token");
        }
        return Optional.empty();
    }

    private SecretKey getSigningKey() {
        byte[] bytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(bytes);
    }

    // DTO para devolver datos extraídos del token
    public record UserTokenData(String email, List<String> roles) {
    }
}