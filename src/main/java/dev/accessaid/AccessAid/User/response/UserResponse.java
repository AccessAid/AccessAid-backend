package dev.accessaid.AccessAid.User.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.accessaid.AccessAid.Profile.response.ProfileResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("username")
    private String username;

    @JsonProperty("email")
    private String email;

    @JsonProperty("profile")
    private ProfileResponse profile;
}
