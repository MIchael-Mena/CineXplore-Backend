package FIUBA.CineXplore.security;

import FIUBA.CineXplore.exception.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
@Validated
@RequiredArgsConstructor
class SessionRestController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TokenDTO> login(
            @NonNull @RequestBody UserLoginDTO data
    ) {
        return userService.loginUser(data)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("RAUL", 0L));
    }
}
