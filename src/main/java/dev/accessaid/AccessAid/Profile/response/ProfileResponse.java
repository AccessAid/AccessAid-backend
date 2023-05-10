package dev.accessaid.AccessAid.Profile.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.accessaid.AccessAid.config.documentation.ExamplesValues;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileResponse {

    @Schema(example = "1", description = "")
    @JsonProperty("id")
    private Integer id;

    @Schema(example = ExamplesValues.FIRSTNAME, description = "")
    @JsonProperty("firstName")
    private String firstName;

    @Schema(example = ExamplesValues.LASTNAME, description = "")
    @JsonProperty("lastName")
    private String lastName;

    @Schema(example = ExamplesValues.EMAIL, description = "")
    @JsonProperty("email")
    private String email;

    @Schema(example = ExamplesValues.USERNAME, description = "")
    @JsonProperty("username")
    private String username;

    @Schema(example = ExamplesValues.AVATAR_PATH, description = "")
    @JsonProperty("avatarPath")
    private String avatarPath;

    @Schema(example = ExamplesValues.STREET_ADDRESS, description = "")
    @JsonProperty("streetAddress")
    private String streetAddress;

    @Schema(example = ExamplesValues.CITY, description = "")
    @JsonProperty("city")
    private String city;

    @Schema(example = ExamplesValues.COUNTRY, description = "")
    @JsonProperty("country")
    private String country;

    @Schema(example = ExamplesValues.ZIPCODE, description = "")
    @JsonProperty("zipCode")
    private String zipCode;

    @Schema(example = ExamplesValues.PHONE, description = "")
    @JsonProperty("phone")
    private String phone;

    @Schema(example = ExamplesValues.ABOUT, description = "")
    @JsonProperty("about")
    private String about;
}
