package dev.accessaid.AccessAid.Ratings.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("rating")
    private double rating;

    @JsonProperty("user_id")
    private Integer user_id;

    @JsonProperty("place_id")
    private Integer place_id;
}
