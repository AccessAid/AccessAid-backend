package dev.accessaid.AccessAid.Profile.service;

import java.util.List;

import dev.accessaid.AccessAid.Profile.exceptions.ProfileNotFoundException;
import dev.accessaid.AccessAid.Profile.exceptions.ProfileSaveException;
import dev.accessaid.AccessAid.Profile.model.Profile;
import dev.accessaid.AccessAid.User.model.User;

public interface ProfileService {

    List<Profile> getAllProfiles();

    Profile getProfileById(Integer id) throws ProfileNotFoundException;

    Profile createProfile(Profile profile) throws ProfileSaveException;

    Profile changeProfile(Profile profile) throws ProfileSaveException, ProfileNotFoundException;

    Profile removeProfile(Integer id) throws ProfileNotFoundException;

    Profile getProfileByUser(User user) throws ProfileNotFoundException;
}
