package dev.accessaid.AccessAid.User.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRequest {

    private String username;

    private String email;

    private String oldPassword;

    private String newPassword;

}
