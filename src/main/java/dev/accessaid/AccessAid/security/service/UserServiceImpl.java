package dev.accessaid.AccessAid.security.service;

import dev.accessaid.AccessAid.model.User;
import dev.accessaid.AccessAid.repository.UserRepository;
import dev.accessaid.AccessAid.security.payload.MessageResponse;
import dev.accessaid.AccessAid.security.payload.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }


    @Override
    public ResponseEntity<MessageResponse> registerUser(RegisterRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername()))
            return new ResponseEntity<>(new MessageResponse("username already exists"), HttpStatus.BAD_REQUEST);

        if (userRepository.existsByEmail(signUpRequest.getEmail()))
            return new ResponseEntity<>(new MessageResponse("email already exists"), HttpStatus.BAD_REQUEST);

        User user = new User(null, signUpRequest.getUsername(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));
        userRepository.save(user);

        return new ResponseEntity<>(new MessageResponse("user was registered correctly"), HttpStatus.CREATED);
    }
}
