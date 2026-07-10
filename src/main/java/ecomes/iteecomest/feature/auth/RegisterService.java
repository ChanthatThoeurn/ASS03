package ecomes.iteecomest.feature.auth;

import ecomes.iteecomest.feature.auth.dto.RegisterRequest;
import ecomes.iteecomest.feature.auth.dto.RegisterResponse;

public interface RegisterService {
    RegisterResponse register(RegisterRequest registerRequest);
}
