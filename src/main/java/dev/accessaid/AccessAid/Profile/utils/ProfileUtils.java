package dev.accessaid.AccessAid.Profile.utils;

import dev.accessaid.AccessAid.Profile.model.Profile;

public class ProfileUtils {

    public static void updateProfileFields(Profile existingProfile, Profile profile) {

        if (profile.getFirstName() != null) {
            existingProfile.setFirstName(profile.getFirstName());
        }
        if (profile.getLastName() != null) {
            existingProfile.setLastName(profile.getLastName());
        }
        if (profile.getAvatarPath() != null) {
            existingProfile.setAvatarPath(profile.getAvatarPath());
        }
        if (profile.getStreetAddress() != null) {
            existingProfile.setStreetAddress(profile.getStreetAddress());
        }
        if (profile.getCity() != null) {
            existingProfile.setCity(profile.getCity());
        }
        if (profile.getCountry() != null) {
            existingProfile.setCountry(profile.getCountry());
        }
        if (profile.getZipCode() != null) {
            existingProfile.setZipCode(profile.getZipCode());
        }
        if (profile.getPhone() != null) {
            existingProfile.setPhone(profile.getPhone());
        }
        if (profile.getAbout() != null) {
            existingProfile.setAbout(profile.getAbout());
        }
    }
}