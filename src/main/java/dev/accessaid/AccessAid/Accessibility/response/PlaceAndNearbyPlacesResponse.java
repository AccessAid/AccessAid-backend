package dev.accessaid.AccessAid.Accessibility.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlaceAndNearbyPlacesResponse {

    @JsonProperty("currentPlace")
    private AccessibilityResponse currentPlace;

    @JsonProperty("nearbyPlaces")
    private List<NearbyPlacesResponse> nearbyPlaces;

    @JsonProperty("nextPageToken")
    private String nextPageToken;

}