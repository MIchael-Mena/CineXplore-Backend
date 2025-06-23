package FIUBA.CineXplore.security.service;

import FIUBA.CineXplore.security.model.Role;
import FIUBA.CineXplore.security.model.RoleName;
import FIUBA.CineXplore.security.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Optional<Role> getByRolName(RoleName roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    public void save(Role rol) {
        roleRepository.save(rol);
    }

    public boolean existsByRoleName(RoleName roleName) {
        return roleRepository.existsByRoleName(roleName);
    }

}
