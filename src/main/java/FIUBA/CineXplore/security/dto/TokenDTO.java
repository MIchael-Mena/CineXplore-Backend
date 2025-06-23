package FIUBA.CineXplore.security.dto;

import lombok.NonNull;

public record TokenDTO(
        @NonNull String accessToken
) {
}
