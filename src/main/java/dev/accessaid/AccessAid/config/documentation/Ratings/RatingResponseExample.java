package dev.accessaid.AccessAid.config.documentation.Ratings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RatingResponseExample {

    @Schema(description = "Unique ID of the rating", example = "1")
    private Integer id;

    @Schema(description = "Rating value", example = "4.5")
    private double rating;

    @Schema(description = "User ID", example = "1")
    private Integer userId;

    @Schema(description = "Place ID", example = "1")
    private Integer placeId;
}
