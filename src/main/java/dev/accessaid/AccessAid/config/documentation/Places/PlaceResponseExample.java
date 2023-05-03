package dev.accessaid.AccessAid.config.documentation.Places;

import dev.accessaid.AccessAid.Places.response.PlaceResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(name = "PlaceResponseExample", description = "Response object for Place")
@Data
public class PlaceResponseExample {

    @Schema(example = "1")
    private Integer id;

    @Schema(example = "48.8556475")
    private Double latitude;

    @Schema(example = "2.2986304")
    private Double longitude;

    @Schema(example = "Champ de Mars, 2 All. Adrienne Lecouvreur, 75007 Paris, France")
    private String address;

    @Schema(example = "ChIJB0gcnCBw5kcRHoIAPcTEApc")
    private String api_placeId;

    @Schema(example = "4.5")
    private Double totalRating = 4.5;

    public static final PlaceResponse EXAMPLE_1 = new PlaceResponse(
            1,
            48.8556475,
            2.2986304,
            "Champ de Mars, 2 All. Adrienne Lecouvreur, 75007 Paris, France",
            "ChIJB0gcnCBw5kcRHoIAPcTEApc",
            4.5);
}
