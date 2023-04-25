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

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

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
        User user = profile.getUser();
        if (user == null) {
            throw new IllegalArgumentException("User does not exist");
        }
        Optional<Profile> profileToSave = profileRepository.findByUser(user);
        if (profileToSave.isPresent()) {
            throw new ProfileSaveException("Profile already exists");
        }
        try {
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
        Profile updatedProfile = profileToUpdate.get();
        return profileRepository.save(updatedProfile);
    }

    @Override
    public Profile removeProfile(Integer id) throws ProfileNotFoundException {
        Optional<Profile> profileToRemove = profileRepository.findById(id);
        if (!profileToRemove.isPresent()) {
            throw new ProfileNotFoundException("Profile not found");
        }
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
