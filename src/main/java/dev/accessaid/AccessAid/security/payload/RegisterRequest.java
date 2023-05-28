package dev.accessaid.AccessAid.security.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "username is required")
    private String username;
    @NotBlank(message = "email is required")
    @Email(message = "wrong email")
    private String email;
    @NotBlank(message = "password is required")
    private String password;
}
