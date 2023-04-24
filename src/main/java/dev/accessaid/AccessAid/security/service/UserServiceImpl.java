package dev.accessaid.AccessAid.security.service;

import dev.accessaid.AccessAid.model.User;
import dev.accessaid.AccessAid.repository.UserRepository;
import dev.accessaid.AccessAid.security.jwt.JwtTokenUtil;
import dev.accessaid.AccessAid.security.payload.JwtResponse;
import dev.accessaid.AccessAid.security.payload.LoginRequest;
import dev.accessaid.AccessAid.security.payload.MessageResponse;
import dev.accessaid.AccessAid.security.payload.RegisterRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtTokenUtil jwtTokenUtil;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder encoder, AuthenticationManager authManager, JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.authManager = authManager;
        this.jwtTokenUtil = jwtTokenUtil;
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

    @Override
    public ResponseEntity<JwtResponse> loginUser(LoginRequest loginRequest) {

        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateJwtToken(authentication);

        return new ResponseEntity<>(new JwtResponse(jwt), HttpStatus.OK);
    }
}
