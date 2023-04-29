package dev.accessaid.AccessAid.User.service;

import java.util.List;

import dev.accessaid.AccessAid.Profile.model.Profile;
import dev.accessaid.AccessAid.User.exceptions.UserNotFoundException;
import dev.accessaid.AccessAid.User.exceptions.UserSaveException;
import dev.accessaid.AccessAid.User.model.User;

public interface UserService {

    List<User> getUsers();

    User getUserById(Integer id) throws UserNotFoundException;

    User createUser(User user) throws UserSaveException;

    User changeUser(User user) throws UserNotFoundException, UserSaveException;

    User removeUser(Integer id) throws UserNotFoundException;

    User getUserByProfile(Profile profile) throws UserNotFoundException;

    User getUserByEmail(String email) throws UserNotFoundException;

    User getUserByUsername(String username) throws UserNotFoundException;

}
