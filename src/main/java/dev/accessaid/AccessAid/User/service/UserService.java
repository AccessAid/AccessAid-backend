package dev.accessaid.AccessAid.User.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import dev.accessaid.AccessAid.User.exceptions.UserNotFoundException;
import dev.accessaid.AccessAid.User.exceptions.UserSaveException;
import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.User.request.UserRequest;
import dev.accessaid.AccessAid.security.payload.LoginRequest;
import dev.accessaid.AccessAid.security.payload.MessageResponse;
import dev.accessaid.AccessAid.security.payload.RegisterRequest;
import dev.accessaid.AccessAid.security.payload.TokenRefreshRequest;

public interface UserService {
    ResponseEntity<MessageResponse> registerUser(RegisterRequest signUpRequest);

    ResponseEntity<MessageResponse> loginUser(LoginRequest loginRequest);

    ResponseEntity<?> refreshtoken(TokenRefreshRequest request);

    Page<User> getUsers(Pageable pageable) throws UserNotFoundException;

    User getUserById(Integer id) throws UserNotFoundException;

    User changeUser(UserRequest user, Integer userId) throws UserNotFoundException, UserSaveException;

    User removeUser(Integer id) throws UserNotFoundException;

    User getUserByProfile(Integer profileId) throws UserNotFoundException;

    User getUserByEmail(String email) throws UserNotFoundException;

    User getUserByUsername(String username) throws UserNotFoundException;

}
