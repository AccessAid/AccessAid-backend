package dev.accessaid.AccessAid.config.documentation.Places;

import com.google.maps.model.AddressType;
import com.google.maps.model.Photo;

import dev.accessaid.AccessAid.Accessibility.response.AccessibilityResponse;
import dev.accessaid.AccessAid.Places.response.PlaceResponse;
import dev.accessaid.AccessAid.config.documentation.ExamplesValues;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "PlaceResponseExample", description = "Response object for Place")
@Data
public class PlaceResponseExample {

    @Schema(example = "1")
    private Integer id;

    @Schema(example = ExamplesValues.LATITUDE)
    private Double latitude;

    @Schema(example = ExamplesValues.LONGITUDE)
    private Double longitude;

    @Schema(example = ExamplesValues.ADDRESS)
    private String address;

    @Schema(example = ExamplesValues.API_PLACE_ID)
    private String api_placeId;

    @Schema(example = "")
    private Double totalRating = 4.5;

    @Schema(example = ExamplesValues.PLACE_NAME, description = "")
    String name;

    @Schema(example = ExamplesValues.PLACE_URL, description = "")
    String url;

    @Schema(example = ExamplesValues.IS_ACCESSIBLE, description = "")
    Boolean isAccessible;

    @Schema(example = ExamplesValues.WEBSITE, description = "")
    String website;

    @Schema(example = ExamplesValues.PLACE_TYPES_STRING, description = "")
    AddressType[] types;

    @Schema(example = ExamplesValues_Photos.PLACE_PHOTOS_STRING, description = "")
    Photo[] photos;

    public static final PlaceResponse EXAMPLE_1 = new PlaceResponse(
            1,
            Double.parseDouble(ExamplesValues.LATITUDE),
            Double.parseDouble(ExamplesValues.LONGITUDE),
            ExamplesValues.ADDRESS,
            ExamplesValues.API_PLACE_ID,
            4.5,
            new AccessibilityResponse(
                    ExamplesValues.PLACE_NAME,
                    ExamplesValues.PLACE_PHONE,
                    ExamplesValues.PLACE_URL,
                    Boolean.parseBoolean(ExamplesValues.IS_ACCESSIBLE),
                    ExamplesValues.WEBSITE,
                    ExamplesValues.PLACE_TYPES,
                    ExamplesValues_Photos.PLACE_PHOTOS));
}
