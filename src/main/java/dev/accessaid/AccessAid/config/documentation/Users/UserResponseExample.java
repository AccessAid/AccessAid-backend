package dev.accessaid.AccessAid.config.documentation.Users;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserResponseExample {

    @Schema(description = "User ID", example = "1")
    private Integer id;

    @Schema(description = "User email", example = "email@email.com")
    private String email;

    @Schema(description = "User username", example = "username")
    private String username;

    @Schema(description = "User profile", example = "1")
    private Integer profile_id;
}
