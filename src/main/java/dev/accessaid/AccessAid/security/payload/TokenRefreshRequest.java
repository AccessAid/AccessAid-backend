package dev.accessaid.AccessAid.security.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TokenRefreshRequest {

    @NotNull(message = "refresh token is required")
    private String refreshToken;
}
