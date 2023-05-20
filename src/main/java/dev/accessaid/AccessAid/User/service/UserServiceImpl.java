package dev.accessaid.AccessAid.User.service;

import java.util.Optional;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import dev.accessaid.AccessAid.Profile.model.Profile;
import dev.accessaid.AccessAid.Profile.repository.ProfileRepository;
import dev.accessaid.AccessAid.User.exceptions.UserNotFoundException;
import dev.accessaid.AccessAid.User.exceptions.UserSaveException;
import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.User.repository.UserRepository;
import dev.accessaid.AccessAid.security.jwt.JwtTokenUtil;
import dev.accessaid.AccessAid.security.payload.JwtResponse;
import dev.accessaid.AccessAid.security.payload.LoginRequest;
import dev.accessaid.AccessAid.security.payload.MessageResponse;
import dev.accessaid.AccessAid.security.payload.RegisterRequest;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public ResponseEntity<MessageResponse> registerUser(RegisterRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername()))
            return new ResponseEntity<>(new MessageResponse("username already exists"), HttpStatus.BAD_REQUEST);

        if (userRepository.existsByEmail(signUpRequest.getEmail()))
            return new ResponseEntity<>(new MessageResponse("email already exists"), HttpStatus.BAD_REQUEST);

        User newUser = new User();
        newUser.setEmail(signUpRequest.getEmail());
        newUser.setUsername(signUpRequest.getUsername());
        newUser.setPassword(encoder.encode(signUpRequest.getPassword()));
        userRepository.save(newUser);
        return new ResponseEntity<>(new MessageResponse("user was registered correctly"), HttpStatus.CREATED);
    }
    @Override
    public ResponseEntity<JwtResponse> loginUser(LoginRequest loginRequest) {

        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateJwtToken(authentication);
        return new ResponseEntity<>(new JwtResponse(jwt), HttpStatus.OK);
    }
    @Override
    public Page<User> getUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getUserById(Integer id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent())
            throw new UserNotFoundException("User not found");

        return user.get();
    }
    @Override
    public User changeUser(User user, Integer userId) throws UserNotFoundException, UserSaveException {
        user.setId(userId);
        Optional<User> userToChange = userRepository.findById(userId);
        if (!userToChange.isPresent())
            throw new UserNotFoundException("User not found");

        if (!userToChange.get().getUsername().equals(user.getUsername()) && userRepository.existsByUsername(user.getUsername()))
            throw new UserSaveException("username already exists");

        if (!userToChange.get().getEmail().equals(user.getEmail()) && userRepository.existsByEmail(user.getEmail()))
            throw new UserSaveException("email already exists");

        User existingUser = userToChange.get();
        if (user.getEmail() != null)
            existingUser.setEmail(user.getEmail());

        if (user.getUsername() != null)
            existingUser.setUsername(user.getUsername());

        if (user.getPassword() != null)
            existingUser.setPassword(encoder.encode(user.getPassword()));

        return userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public User removeUser(Integer id) throws UserNotFoundException {
        Optional<User> userToDelete = userRepository.findById(id);
        if (!userToDelete.isPresent())
            throw new UserNotFoundException("User not found");

        userRepository.deleteById(id);

        Optional<Profile> profileToDelete = profileRepository.findByUser(userToDelete.get());
        if (profileToDelete.isPresent())
            profileRepository.delete(profileToDelete.get());

        return userToDelete.get();
    }
    @Override
    public User getUserByProfile(Integer profileId) throws UserNotFoundException {
        Optional<User> user = userRepository.findByProfileId(profileId);
        if (!user.isPresent())
            throw new UserNotFoundException("User not found -profile does not exists");

        return user.get();
    }
    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent())
            throw new UserNotFoundException("User not found -email does not exists");

        return user.get();
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent())
            throw new UserNotFoundException("User not found -username does not exists");

        return user.get();
    }
}
