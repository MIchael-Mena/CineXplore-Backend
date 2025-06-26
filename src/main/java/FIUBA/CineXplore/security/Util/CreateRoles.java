package FIUBA.CineXplore.security.Util;

import FIUBA.CineXplore.security.model.Role;
import FIUBA.CineXplore.security.model.RoleName;
import FIUBA.CineXplore.security.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateRoles implements CommandLineRunner {


    private final RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        createRoleIfNotExists(RoleName.ADMIN);
        createRoleIfNotExists(RoleName.USER);
    }

    private void createRoleIfNotExists(RoleName roleName) {
        if (!roleService.existsByRoleName(roleName)) {
            Role role = new Role(roleName);
            roleService.save(role);
//            System.out.println("Rol creado: " + roleName);
        }
    }

}
