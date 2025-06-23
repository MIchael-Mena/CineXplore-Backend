package FIUBA.CineXplore.security.service;

import FIUBA.CineXplore.model.User;
import FIUBA.CineXplore.repository.UserRepository;
import FIUBA.CineXplore.security.jwt.JwtService;
import FIUBA.CineXplore.security.model.MainUser;
import FIUBA.CineXplore.security.model.Role;
import FIUBA.CineXplore.security.model.RoleName;
import FIUBA.CineXplore.security.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(MainUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + email));
    }

    public String login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Credenciales inválidas"));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }
        return createToken(user);
    }

    public String createToken(User user) {
        return jwtService.createToken(user);
    }

    public User createUser(String username, String email, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("El email ya está registrado");
        }
        if (userRepository.existsByUserName(username)) {
            throw new IllegalArgumentException("El nombre de usuario ya está registrado");
        }

/*        Role userRole = roleRepository.findByRoleName(RoleName.ADMIN)
                .orElseThrow(() -> new IllegalStateException("Rol USER no encontrado"));*/
        Role userRole = roleRepository.findByRoleName(RoleName.USER)
                .orElseThrow(() -> new IllegalStateException("Rol USER no encontrado"));

        User user = new User();
        user.setUserName(username);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setRoles(Set.of(userRole));
        return userRepository.save(user);
    }
}