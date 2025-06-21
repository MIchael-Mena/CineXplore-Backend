package FIUBA.CineXplore.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public Optional<TokenDTO> createUser(UserCreateDTO data) {
        if (!EmailValidator.isValidEmail(data.email())) {
            throw new EmailInvalidoException("El email proporcionado no es válido: " + data.email());
        }

        if (userRepository.findByUsername(data.username()).isPresent()) {
            return loginUser(data);
        } else {
            var user = data.asUser(passwordEncoder::encode);
            userRepository.save(user);
            
            switch (data.role().toUpperCase()) {
                case "ATLETA" -> {
                    Atleta atleta = new Atleta();
                    atleta.setUser(user);
                    if (data.edad() != null) {
                        if (data.edad() <= 13 || data.edad() > 100) {
                            throw new EdadInvalidaException("La edad debe ser mayor que 13 años.");
                        }
                        atleta.setEdad(data.edad());
                    }
                    if (data.peso() != null) {
                        if (data.peso() <= 0 || data.peso() > 200) {
                            throw new IllegalArgumentException("El peso debe ser mayor que 0 y menor o igual a 200 kg.");
                        }
                        atleta.setPeso(data.peso());
                    }
                    if (data.altura() != null) {
                        if (data.altura() <= 0 || data.altura() > 250) {
                            throw new IllegalArgumentException("La altura debe ser mayor que 0 y menor o igual a 250 cm.");
                        }
                        atleta.setAltura(data.altura());
                    }
                    atleta.setNombre(data.nombre());
                    atleta.setApellido(data.apellido());
                    atleta.setEmail(data.email());
                    atletaRepository.save(atleta);

                    if (data.peso() != null) {
                        RegistroPesoAtletaDTO pesoDto = new RegistroPesoAtletaDTO(data.peso());
                        atletaService.registrarPeso(user.getUsername(), pesoDto);
                    }
                    
                    RecomendacionNutricionalDTO recomendacionDto = atletaService.calcularRecomendacionNutricional(user.getUsername());
                    atletaService.guardarRecomendacionNutricional(atleta, recomendacionDto);
                }
                case "ENTRENADOR" -> {
                    if (!matriculaEntrenadorRepository.existsById(data.matricula())) {
                        throw new MatriculaInvalidaException("Matrícula de entrenador no valida: " + data.matricula());
                    }
                    Entrenador entrenador = new Entrenador();
                    entrenador.setUser(user);
                    entrenador.setMatriculaProfesional(data.matricula());
                    entrenador.setNombre(data.nombre());
                    entrenador.setApellido(data.apellido());
                    entrenador.setEmail(data.email());
                    entrenadorRepository.save(entrenador);
                }
                case "NUTRICIONISTA" -> {
                    System.out.println("Matrícula recibida: " + data.matricula());
                    if (!matriculaNutricionistaRepository.existsById(data.matricula())) {
                        throw new MatriculaInvalidaException("Matrícula de nutricionista no válida: " + data.matricula());
                    }
                    Nutricionista nutricionista = new Nutricionista();
                    nutricionista.setUser(user);
                    nutricionista.setMatricula_profesional(data.matricula());
                    nutricionista.setNombre(data.nombre());
                    nutricionista.setApellido(data.apellido());
                    nutricionista.setEmail(data.email());
                    System.out.println("Nutricionista a guardar: " + nutricionista);
                    nutricionistaRepository.save(nutricionista);
                }
                default -> throw new IllegalArgumentException("Rol no válido: " + data.role());
            }
            return Optional.of(generateTokens(user));
        }
    }

    public Optional<TokenDTO> loginUser(UserCredentials data) {
        Optional<User> maybeUser = userRepository.findByUsername(data.username());
        return maybeUser
                .filter(user -> passwordEncoder.matches(data.password(),  user.password()))
                .map(this::generateTokens);
    }

    private TokenDTO generateTokens(User user) {
        String accessToken = jwtService.createToken(new JwtUserDetails(
                user.getUsername(),
                "ROLE_" + user.getRole()
        ));
        return new TokenDTO(accessToken);
    }

}