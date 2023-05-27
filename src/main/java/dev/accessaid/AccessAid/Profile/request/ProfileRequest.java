package dev.accessaid.AccessAid.Profile.request;

import lombok.Data;

@Data
public class ProfileRequest {

    String firstName;

    String lastName;

    String avatarPath;

    String streetAddress;

    String city;

    String country;

    String zipCode;

    String phone;

    String about;

    Integer userId;

    String email;

    String oldPassword;

    String newPassword;

    String username;

}
