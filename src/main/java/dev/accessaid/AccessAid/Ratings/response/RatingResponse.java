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

    @JsonProperty("userId")
    private Integer userId;

    @JsonProperty("placeId")
    private Integer placeId;
}
