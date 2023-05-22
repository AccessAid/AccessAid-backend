package dev.accessaid.AccessAid.Profile.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dev.accessaid.AccessAid.Profile.exceptions.ProfileNotFoundException;
import dev.accessaid.AccessAid.Profile.exceptions.ProfileSaveException;
import dev.accessaid.AccessAid.Profile.model.Profile;
public interface ProfileService {
    Page<Profile> getAllProfiles(Pageable pageable) throws ProfileNotFoundException;
    Profile getProfileById(Integer id) throws ProfileNotFoundException;
    Profile createProfile(Profile profile) throws ProfileSaveException;
    Profile changeProfile(Profile profile, Integer profileId) throws ProfileSaveException, ProfileNotFoundException;
    Profile removeProfile(Integer id) throws ProfileNotFoundException;
    Profile getProfileByUser(Integer userId) throws ProfileNotFoundException;
}
