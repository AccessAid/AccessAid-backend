package dev.accessaid.AccessAid.config.documentation.Places;

import dev.accessaid.AccessAid.config.documentation.ExamplesValues;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class PlaceRequestExample {

    @Schema(description = "Address of the place", example = ExamplesValues.ADDRESS)
    private String address;

    @Schema(description = "Latitude of the place", example = ExamplesValues.LATITUDE)
    private Double latitude;

    @Schema(description = "Longitude of the place", example = ExamplesValues.LONGITUDE)
    private Double longitude;

}
