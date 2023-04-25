package dev.accessaid.AccessAid.User.utils;

import java.util.List;
import java.util.stream.Collectors;

import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.User.response.UserResponse;

public class UserMapper {

    public static UserResponse toUserResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail());
    }

    public static List<UserResponse> toUserResponses(List<User> users) {
        return users.stream().map(UserMapper::toUserResponse).collect(Collectors.toList());
    }

}
