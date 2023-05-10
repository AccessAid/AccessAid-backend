package dev.accessaid.AccessAid.User.utils;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import dev.accessaid.AccessAid.Profile.model.Profile;
import dev.accessaid.AccessAid.Profile.response.ProfileResponse;
import dev.accessaid.AccessAid.Profile.utils.ProfileMapper;
import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.User.response.UserResponse;

public class UserMapper {

    public static UserResponse toUserResponse(User user) {
        ProfileResponse profileResponse = null;
        if (user.getProfile() != null) {
            Profile profile = user.getProfile();
            profileResponse = ProfileMapper.toProfileResponse(profile);
        }

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                profileResponse);

    }

    public static List<UserResponse> toUserResponses(List<User> users) {
        return users.stream().map(UserMapper::toUserResponse).collect(Collectors.toList());
    }

    public static Page<UserResponse> toUserResponses(Page<User> users, Pageable pageable) {
        List<UserResponse> userResponses = users.stream().map(UserMapper::toUserResponse).collect(Collectors.toList());
        return new PageImpl<>(userResponses, pageable, users.getTotalElements());
    }
}
