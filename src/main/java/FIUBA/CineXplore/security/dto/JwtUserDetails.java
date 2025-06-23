package FIUBA.CineXplore.security.dto;

public record JwtUserDetails(
        String username,
        String role
) {
}