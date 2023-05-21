package dev.accessaid.AccessAid.security.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class JwtResponse {

    private String token;

    private String refreshToken;

    private String expiration;

}
