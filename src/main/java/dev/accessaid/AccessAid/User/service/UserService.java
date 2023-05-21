package dev.accessaid.AccessAid.User.service;

import java.util.List;
import dev.accessaid.AccessAid.security.payload.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import dev.accessaid.AccessAid.User.exceptions.UserNotFoundException;
import dev.accessaid.AccessAid.User.exceptions.UserSaveException;
import dev.accessaid.AccessAid.User.model.User;

public interface UserService {
    ResponseEntity<MessageResponse> registerUser(RegisterRequest signUpRequest);
    ResponseEntity<MessageResponse> loginUser(LoginRequest loginRequest);

    ResponseEntity<?> refreshtoken(TokenRefreshRequest request);

    List<User> getUsers() throws UserNotFoundException;
    Page<User> getUsers(Pageable pageable) throws UserNotFoundException;
    User getUserById(Integer id) throws UserNotFoundException;
    User changeUser(User user, Integer userId) throws UserNotFoundException, UserSaveException;
    User removeUser(Integer id) throws UserNotFoundException;
    User getUserByProfile(Integer profileId) throws UserNotFoundException;
    User getUserByEmail(String email) throws UserNotFoundException;
    User getUserByUsername(String username) throws UserNotFoundException;

}
