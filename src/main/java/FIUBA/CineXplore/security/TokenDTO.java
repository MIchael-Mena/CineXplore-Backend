package FIUBA.CineXplore.security;

import lombok.NonNull;

public record TokenDTO(
        @NonNull String accessToken
) {
}
