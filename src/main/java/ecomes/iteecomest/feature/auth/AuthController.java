package ecomes.iteecomest.feature.auth;

import ecomes.iteecomest.feature.auth.dto.RegisterRequest;
import ecomes.iteecomest.feature.auth.dto.RegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final RegisterService registerService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public RegisterResponse register(@Valid @RequestBody RegisterRequest registerRequest) {
        return registerService.register(registerRequest);
    }

}
