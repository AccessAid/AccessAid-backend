package dev.accessaid.AccessAid.Accessibility.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NearbyPlacesResponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty("latitude")
    private Double latitude;

    @JsonProperty("longitude")
    private Double longitude;

    @JsonProperty("placeId")
    private String placeId;

    @JsonProperty("accessibility")
    private Boolean wheelchairAccessibleEntrance;

}