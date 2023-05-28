package dev.accessaid.AccessAid.Ratings.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalRatingResponse {

    @JsonProperty("placeId")
    private Integer placeId;

    @JsonProperty("totalRating")
    private double totalRating;

}
