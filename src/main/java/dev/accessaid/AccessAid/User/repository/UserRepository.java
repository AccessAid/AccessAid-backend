package dev.accessaid.AccessAid.User.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.accessaid.AccessAid.Profile.model.Profile;
import dev.accessaid.AccessAid.User.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByProfile(Profile profile);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

}
