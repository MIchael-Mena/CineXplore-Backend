package FIUBA.CineXplore.security.dto;

import FIUBA.CineXplore.model.User;
import lombok.Data;


@Data
public class UserDTO {
    private String id;
    private String username;
    private String email;
    private String[] roles;
    private String avatarUrl;

    public static UserDTO fromUser(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getUserId().toString());
        dto.setUsername(user.getUserName());
        dto.setEmail(user.getEmail());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setRoles(
                user.getRoles() != null && !user.getRoles().isEmpty()
                        ? user.getRoles().stream().map(role -> role.getRoleName().name()).toArray(String[]::new)
                        : new String[]{"USER"}
        );
        return dto;
    }
}
