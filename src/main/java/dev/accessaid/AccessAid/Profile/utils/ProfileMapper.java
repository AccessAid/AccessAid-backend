package dev.accessaid.AccessAid.Profile.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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

    public static Page<ProfileResponse> toProfileResponses(Page<Profile> profiles, Pageable pageable) {
        List<ProfileResponse> profileResponses = profiles.stream()
                .map(ProfileMapper::toProfileResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(profileResponses, pageable, profiles.getTotalElements());
    }

}
