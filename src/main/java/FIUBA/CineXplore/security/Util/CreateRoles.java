package FIUBA.CineXplore.security.Util;

import FIUBA.CineXplore.security.model.Role;
import FIUBA.CineXplore.security.model.RoleName;
import FIUBA.CineXplore.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CreateRoles implements CommandLineRunner {

    @Autowired
    RoleService roleService;

    @Override
    public void run(String... args) throws Exception {
        if (!roleService.existsByRoleName(RoleName.ADMIN)) {
            Role rolAdmin = new Role(RoleName.ADMIN);
            roleService.save(rolAdmin);
        }
        if (!roleService.existsByRoleName(RoleName.USER)) {
            Role rolUser = new Role(RoleName.USER);
            roleService.save(rolUser);
        }
    }

}
