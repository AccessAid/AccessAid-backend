package dev.accessaid.AccessAid.security.service;

import dev.accessaid.AccessAid.security.payload.MessageResponse;
import dev.accessaid.AccessAid.security.payload.RegisterRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<MessageResponse> registerUser(RegisterRequest signUpRequest);
}
