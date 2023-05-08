package dev.accessaid.AccessAid.config.documentation.Users;

import dev.accessaid.AccessAid.config.documentation.ExamplesValues;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class LoginRequestExample {

    @Schema(example = ExamplesValues.USERNAME)
    private String username;

    @Schema(example = ExamplesValues.PASSWORD)
    private String password;
}
