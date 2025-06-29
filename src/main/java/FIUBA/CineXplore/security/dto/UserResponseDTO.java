package FIUBA.CineXplore.security.dto;

import FIUBA.CineXplore.model.User;
import lombok.Data;


@Data
public class UserResponseDTO {
    private String id;
    private String username;
    private String email;
    private String[] roles;
    private String avatarUrl;
    private String birthDate;
    private String country;

    public static UserResponseDTO fromUser(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getUserId().toString());
        dto.setUsername(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setBirthDate(user.getBirthDate() != null ? user.getBirthDate().toString() : null);
        dto.setCountry(user.getCountry());
        dto.setRoles(
                user.getRoles() != null && !user.getRoles().isEmpty()
                        ? user.getRoles().stream().map(role -> role.getRoleName().name()).toArray(String[]::new)
                        : new String[]{"USER"}
        );
        return dto;
    }
}
