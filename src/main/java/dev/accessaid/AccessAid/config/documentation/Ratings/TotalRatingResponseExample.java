package dev.accessaid.AccessAid.config.documentation.Ratings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TotalRatingResponseExample {

    @Schema(description = "Place Id", example = "1")
    private Integer place_id;

    @Schema(description = "Total Rating", example = "4.5")
    private double totalRating;
}
