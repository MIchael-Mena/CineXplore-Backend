package FIUBA.CineXplore.security;

public record JwtUserDetails (
        String username,
        String role
) {}