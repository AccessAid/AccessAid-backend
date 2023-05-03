package dev.accessaid.AccessAid.Profile.utils;

import java.util.List;
import java.util.stream.Collectors;

import dev.accessaid.AccessAid.Profile.model.Profile;
import dev.accessaid.AccessAid.Profile.response.ProfileResponse;

public class ProfileMapper {

    public static ProfileResponse toProfileResponse(Profile profile) {
        return new ProfileResponse(
                profile.getId(),
                profile.getFirstName(),
                profile.getLastName(),
                profile.getUser().getEmail(),
                profile.getUser().getUsername(),
                profile.getAvatarPath()

        );

    }

    public static List<ProfileResponse> toProfileResponses(List<Profile> profiles) {
        return profiles.stream().map(ProfileMapper::toProfileResponse).collect(Collectors.toList());
    }

}
