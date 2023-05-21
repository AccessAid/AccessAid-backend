package dev.accessaid.AccessAid.security.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TokenRefreshResponse {

    private String token;
    private String refreshToken;
    private String tokenType = "Bearer";
    private String expiration;

    public TokenRefreshResponse(String token, String refreshToken, String expiration) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.expiration = expiration;
    }
}
