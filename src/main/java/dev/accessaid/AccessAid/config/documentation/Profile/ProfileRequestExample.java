package dev.accessaid.AccessAid.config.documentation.Profile;

import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.config.documentation.ExamplesValues;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
public class ProfileRequestExample {

    @Schema(example = ExamplesValues.FIRSTNAME, description = "")
    String firstName;

    @Schema(example = ExamplesValues.LASTNAME, description = "")
    String lastName;

    @Schema(example = ExamplesValues.AVATAR_PATH, description = "")
    String avatarPath;

    @Schema(description = "user", example = "{\"id\":1}")
    @OneToOne
    @JoinColumn(name = "user_id")
    User user;

}
