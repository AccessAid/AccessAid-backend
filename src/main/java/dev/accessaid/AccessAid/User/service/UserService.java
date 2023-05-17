package dev.accessaid.AccessAid.User.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import dev.accessaid.AccessAid.Profile.model.Profile;
import dev.accessaid.AccessAid.User.exceptions.UserNotFoundException;
import dev.accessaid.AccessAid.User.exceptions.UserSaveException;
import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.security.payload.JwtResponse;
import dev.accessaid.AccessAid.security.payload.LoginRequest;
import dev.accessaid.AccessAid.security.payload.MessageResponse;
import dev.accessaid.AccessAid.security.payload.RegisterRequest;

public interface UserService {
    ResponseEntity<MessageResponse> registerUser(RegisterRequest signUpRequest);

    ResponseEntity<JwtResponse> loginUser(LoginRequest loginRequest);

    List<User> getUsers() throws UserNotFoundException;

    Page<User> getUsers(Pageable pageable) throws UserNotFoundException;

    User getUserById(Integer id) throws UserNotFoundException;

    User createUser(User user) throws UserSaveException;

    User changeUser(User user) throws UserNotFoundException, UserSaveException;

    User removeUser(Integer id) throws UserNotFoundException;

    User getUserByProfile(Integer profileId) throws UserNotFoundException;

    User getUserByEmail(String email) throws UserNotFoundException;

    User getUserByUsername(String username) throws UserNotFoundException;

}
