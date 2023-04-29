package dev.accessaid.AccessAid.User.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.accessaid.AccessAid.Profile.model.Profile;
import dev.accessaid.AccessAid.Profile.repository.ProfileRepository;
import dev.accessaid.AccessAid.User.exceptions.UserNotFoundException;
import dev.accessaid.AccessAid.User.exceptions.UserSaveException;
import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.User.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Integer id) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found");

        }
        return user.get();
    }

    @Override
    public User createUser(User user) throws UserSaveException {
        Optional<User> userToCreate = userRepository.findByEmail(user.getEmail());
        if (userToCreate.isPresent()) {
            throw new UserSaveException("User already exists");
        }
        return userRepository.save(user);
    }

    @Override
    public User changeUser(User user) throws UserNotFoundException, UserSaveException {
        Optional<User> userToChange = userRepository.findById(user.getId());
        if (!userToChange.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        return userRepository.save(user);
    }

    @Override
    public User removeUser(Integer id) throws UserNotFoundException {
        Optional<User> userToDelete = userRepository.findById(id);
        if (!userToDelete.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        userRepository.deleteById(id);

        Optional<Profile> profileToDelete = profileRepository.findByUser(userToDelete.get());
        if (profileToDelete.isPresent()) {
            profileRepository.delete(profileToDelete.get());
        }
        return userToDelete.get();

    }

    @Override
    public User getUserByProfile(Profile profile) throws UserNotFoundException {
        Optional<User> user = userRepository.findByProfile(profile);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        return user.get();

    }

    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        return user.get();

    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if (!user.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        return user.get();

    }
}
