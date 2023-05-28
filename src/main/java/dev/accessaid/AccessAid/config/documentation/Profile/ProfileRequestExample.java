package dev.accessaid.AccessAid.config.documentation.Profile;

import dev.accessaid.AccessAid.config.documentation.ExamplesValues;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProfileRequestExample {

    @Schema(example = ExamplesValues.FIRSTNAME, description = "")
    String firstName;

    @Schema(example = ExamplesValues.LASTNAME, description = "")
    String lastName;

    @Schema(example = ExamplesValues.AVATAR_PATH, description = "")
    String avatarPath;

    @Schema(example = ExamplesValues.STREET_ADDRESS, description = "")
    String streetAddress;

    @Schema(example = ExamplesValues.CITY, description = "")
    String city;

    @Schema(example = ExamplesValues.COUNTRY, description = "")
    String country;

    @Schema(example = ExamplesValues.ZIPCODE, description = "")
    String zipCode;

    @Schema(example = ExamplesValues.PHONE, description = "")
    String phone;

    @Schema(example = ExamplesValues.ABOUT, description = "")
    String about;

    @Schema(description = "user", example = "1")
    Integer userId;

    @Schema(example = ExamplesValues.EMAIL, description = "")
    String email;

    @Schema(example = ExamplesValues.PASSWORD, description = "")
    String oldPassword;

    @Schema(example = ExamplesValues.PASSWORD, description = "")
    String newPassword;

    @Schema(example = ExamplesValues.USERNAME, description = "")
    String username;

}
