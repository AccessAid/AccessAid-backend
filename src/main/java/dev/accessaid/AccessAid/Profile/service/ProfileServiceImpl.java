package dev.accessaid.AccessAid.Profile.service;

import java.util.List;
import java.util.Optional;

import dev.accessaid.AccessAid.User.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dev.accessaid.AccessAid.Profile.exceptions.ProfileNotFoundException;
import dev.accessaid.AccessAid.Profile.exceptions.ProfileSaveException;
import dev.accessaid.AccessAid.Profile.model.Profile;
import dev.accessaid.AccessAid.Profile.repository.ProfileRepository;
import dev.accessaid.AccessAid.Profile.utils.ProfileUtils;
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
    public List<Profile> getAllProfiles() throws ProfileNotFoundException {
        return profileRepository.findAll();
    }

    @Override
    public Page<Profile> getAllProfiles(Pageable pageable) {
        return profileRepository.findAll(pageable);
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
                .orElseThrow(() -> new UserNotFoundException("User does not exist"));

        if (user.getProfile() != null)
            throw new ProfileSaveException("Profile already exists");

        profile.setUser(user);
        user.setProfile(profile);

        return profileRepository.save(profile);
    }

    @Override
    public Profile changeProfile(Profile profile) throws ProfileSaveException, ProfileNotFoundException {
        Optional<Profile> profileToUpdate = profileRepository.findById(profile.getId());
        if (!profileToUpdate.isPresent()) {
            throw new ProfileNotFoundException("Profile not found");
        }

        Profile existingProfile = profileToUpdate.get();

        ProfileUtils.updateProfileFields(existingProfile, profile);
        // if (profile.getFirstName() != null) {
        // existingProfile.setFirstName(profile.getFirstName());
        // }
        // if (profile.getLastName() != null) {
        // existingProfile.setLastName(profile.getLastName());
        // }
        // if (profile.getAvatarPath() != null) {
        // existingProfile.setAvatarPath(profile.getAvatarPath());
        // }
        // if (profile.getStreetAddress() != null) {
        // existingProfile.setStreetAddress(profile.getStreetAddress());
        // }
        // if (profile.getCity() != null) {
        // existingProfile.setCity(profile.getCity());
        // }
        // if (profile.getCountry() != null) {
        // existingProfile.setCountry(profile.getCountry());
        // }
        // if (profile.getZipCode() != null) {
        // existingProfile.setZipCode(profile.getZipCode());
        // }
        // if (profile.getPhone() != null) {
        // existingProfile.setPhone(profile.getPhone());
        // }
        // if (profile.getAbout() != null) {
        // existingProfile.setAbout(profile.getAbout());
        // }

        try {
            return profileRepository.save(existingProfile);
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
    public Profile getProfileByUser(Integer userId) throws ProfileNotFoundException {

        Optional<User> userFind = userRepository.findById(userId);
        if (!userFind.isPresent())
            throw new UserNotFoundException("User not found");

        Optional<Profile> profile = profileRepository.findByUser(userFind.get());
        if (!profile.isPresent())
            throw new ProfileNotFoundException("Profile not found");

        return profile.get();

    }

}
