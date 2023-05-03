package dev.accessaid.AccessAid.Ratings.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TotalRatingResponse {

    @JsonProperty("place_id")
    private Integer place_id;

    @JsonProperty("totalRating")
    private double totalRating;

}
