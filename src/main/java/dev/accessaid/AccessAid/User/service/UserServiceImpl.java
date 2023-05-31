package dev.accessaid.AccessAid.User.service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

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

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Comments.repository.CommentRepository;
import dev.accessaid.AccessAid.Comments.service.CommentServiceImpl;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Profile.model.Profile;
import dev.accessaid.AccessAid.Profile.repository.ProfileRepository;
import dev.accessaid.AccessAid.User.exceptions.UserNotFoundException;
import dev.accessaid.AccessAid.User.exceptions.UserSaveException;
import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.User.repository.UserRepository;
import dev.accessaid.AccessAid.User.request.UserRequest;
import dev.accessaid.AccessAid.security.exceptions.TokenRefreshException;
import dev.accessaid.AccessAid.security.jwt.JwtTokenUtil;
import dev.accessaid.AccessAid.security.model.RefreshToken;
import dev.accessaid.AccessAid.security.payload.JwtResponse;
import dev.accessaid.AccessAid.security.payload.LoginRequest;
import dev.accessaid.AccessAid.security.payload.MessageResponse;
import dev.accessaid.AccessAid.security.payload.RegisterRequest;
import dev.accessaid.AccessAid.security.payload.TokenRefreshRequest;
import dev.accessaid.AccessAid.security.payload.TokenRefreshResponse;
import dev.accessaid.AccessAid.security.service.RefreshTokenService;
import jakarta.transaction.Transactional;

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

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private CommentServiceImpl commentService;

    @Autowired
    private CommentRepository commentRepository;

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
    public ResponseEntity<MessageResponse> loginUser(LoginRequest loginRequest) {
        if (Boolean.FALSE.equals(userRepository.existsByUsername(loginRequest.getUsername())))
            return new ResponseEntity<>(new MessageResponse("this username doesn't exist"), HttpStatus.BAD_REQUEST);

        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtTokenUtil.generateJwtToken(authentication);
        Instant expiration = jwtTokenUtil.extractTokenExpiration(jwt);
        ZoneId cetZone = ZoneId.of("CET");
        ZonedDateTime expirationCET = ZonedDateTime.ofInstant(expiration, cetZone);

        User user = userRepository.findByUsername(loginRequest.getUsername()).get();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

        return new ResponseEntity<>(new JwtResponse(jwt, refreshToken.getToken(), expirationCET.toString()),
                HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> refreshtoken(TokenRefreshRequest request) {

        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtTokenUtil.generateTokenFromUsername(user.getUsername());
                    Instant expiration = jwtTokenUtil.extractTokenExpiration(token);
                    ZoneId cetZone = ZoneId.of("CET");
                    ZonedDateTime expirationCET = ZonedDateTime.ofInstant(expiration, cetZone);

                    RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());

                    return ResponseEntity
                            .ok(new TokenRefreshResponse(token, refreshToken.getToken(), expirationCET.toString()));
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
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
    public User changeUser(UserRequest user, Integer userId) throws UserNotFoundException, UserSaveException {

        Optional<User> userToChange = userRepository.findById(userId);
        if (!userToChange.isPresent())
            throw new UserNotFoundException("User not found");

        if (!userToChange.get().getUsername().equals(user.getUsername())
                && userRepository.existsByUsername(user.getUsername()))
            throw new UserSaveException("username already exists");

        if (!userToChange.get().getEmail().equals(user.getEmail()) && userRepository.existsByEmail(user.getEmail()))
            throw new UserSaveException("email already exists");

        User existingUser = userToChange.get();
        if (user.getEmail() != null)
            existingUser.setEmail(user.getEmail());

        if (user.getUsername() != null)
            existingUser.setUsername(user.getUsername());

        if (user.getNewPassword() != null && user.getNewPassword() != "")
            if (encoder.matches(user.getOldPassword(), existingUser.getPassword())) {

                existingUser.setPassword(encoder.encode(user.getNewPassword()));
            } else {
                throw new UserSaveException("oldPassword doesn't match with your current password.");
            }
        return userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public User removeUser(Integer id) throws UserNotFoundException {
        Optional<User> userToDelete = userRepository.findById(id);
        if (!userToDelete.isPresent())
            throw new UserNotFoundException("User not found");

        List<Comment> commentsToDelete = commentRepository.findAllCommentsByUser(userToDelete.get());
        for (Comment comment : commentsToDelete) {
            commentService.removeComment(comment.getId());
        }

        List<Place> places = userToDelete.get().getPlaces();
        for (Place place : places) {
            place.getUsers().remove(userToDelete.get());
        }

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
