package dev.accessaid.AccessAid.Profile.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.accessaid.AccessAid.Profile.model.Profile;
import dev.accessaid.AccessAid.User.model.User;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {

    Optional<Profile> findByUser(User user);

}
