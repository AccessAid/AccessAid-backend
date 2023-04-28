package dev.accessaid.AccessAid.Profile.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.accessaid.AccessAid.Profile.exceptions.ProfileNotFoundException;
import dev.accessaid.AccessAid.Profile.exceptions.ProfileSaveException;
import dev.accessaid.AccessAid.Profile.model.Profile;
import dev.accessaid.AccessAid.Profile.repository.ProfileRepository;
import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.User.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public List<Profile> getAllProfiles() {
        return profileRepository.findAll();
    }

    @Override
    public Profile getProfileById(Integer id) throws ProfileNotFoundException {
        Optional<Profile> profile = profileRepository.findById(id);
        if (!profile.isPresent()) {
            throw new ProfileNotFoundException("Profile not found");
        }
        return profile.get();
    }

    @Override
    public Profile createProfile(Profile profile) throws ProfileSaveException {
        User user = userRepository.findById(profile.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("User does not exist"));

        if (user.getProfile() != null) {
            throw new ProfileSaveException("Profile already exists");
        }
        try {
            profile.setUser(user);
            user.setProfile(profile);
            return profileRepository.save(profile);
        } catch (Exception e) {
            throw new ProfileSaveException("Error saving profile");
        }
    }

    @Override
    public Profile changeProfile(Profile profile) throws ProfileSaveException, ProfileNotFoundException {
        Optional<Profile> profileToUpdate = profileRepository.findById(profile.getId());
        if (!profileToUpdate.isPresent()) {
            throw new ProfileNotFoundException("Profile not found");
        }
        try {
            return profileRepository.save(profile);
        } catch (Exception e) {
            throw new ProfileSaveException("Error saving profile");
        }
    }

    @Override
    @Transactional
    public Profile removeProfile(Integer id) throws ProfileNotFoundException {
        Optional<Profile> profileToRemove = profileRepository.findById(id);
        if (!profileToRemove.isPresent()) {
            throw new ProfileNotFoundException("Profile not found");
        }
        Profile deletedProfile = profileToRemove.get();
        User user = deletedProfile.getUser();
        user.setProfile(null);
        profileRepository.deleteById(id);
        return profileToRemove.get();
    }

    @Override
    public Profile getProfileByUser(User user) throws ProfileNotFoundException {
        Optional<Profile> profile = profileRepository.findByUser(user);
        if (!profile.isPresent()) {
            throw new ProfileNotFoundException("Profile not found");
        }
        return profile.get();

    }

}