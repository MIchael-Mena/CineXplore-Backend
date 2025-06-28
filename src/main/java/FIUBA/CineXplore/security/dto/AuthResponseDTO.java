package FIUBA.CineXplore.security.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String token;
    private UserDTO user;
}
