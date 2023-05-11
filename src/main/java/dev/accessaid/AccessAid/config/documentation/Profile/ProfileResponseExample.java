package dev.accessaid.AccessAid.config.documentation.Profile;

import dev.accessaid.AccessAid.config.documentation.ExamplesValues;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ProfileResponseExample {

    @Schema(example = "1", description = "Unique ID of the profile")
    private Integer id;

    @Schema(example = ExamplesValues.FIRSTNAME, description = "")
    private String firstName;

    @Schema(example = ExamplesValues.LASTNAME, description = "")
    private String lastName;

    @Schema(example = ExamplesValues.EMAIL, description = "")
    private String email;

    @Schema(example = ExamplesValues.USERNAME, description = "")
    private String username;

    @Schema(example = ExamplesValues.AVATAR_PATH, description = "")
    private String avatarPath;

    @Schema(example = ExamplesValues.STREET_ADDRESS, description = "")
    private String streetAddress;

    @Schema(example = ExamplesValues.CITY, description = "")
    private String city;

    @Schema(example = ExamplesValues.COUNTRY, description = "")
    private String country;

    @Schema(example = ExamplesValues.ZIPCODE, description = "")
    private String zipCode;

    @Schema(example = ExamplesValues.PHONE, description = "")
    private String phone;

    @Schema(example = ExamplesValues.ABOUT, description = "")
    private String about;

}
