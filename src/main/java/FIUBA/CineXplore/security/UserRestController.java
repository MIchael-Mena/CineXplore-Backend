package FIUBA.CineXplore.security;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Validated
@RequiredArgsConstructor
class UserRestController {
    private final UserService userService;

    @PostMapping
    public ResponseEntity<TokenDTO> signUp(
            @NonNull @RequestBody UserCreateDTO data
    ) {
        return userService.createUser(data)
                .map(tk -> ResponseEntity.status(HttpStatus.CREATED).body(tk))
                .orElse(ResponseEntity.status(HttpStatus.CONFLICT).build());
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDTO> getMyProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        JwtUserDetails currentUser = (JwtUserDetails) authentication.getPrincipal();
        return userService.getUserProfile(currentUser.username())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
