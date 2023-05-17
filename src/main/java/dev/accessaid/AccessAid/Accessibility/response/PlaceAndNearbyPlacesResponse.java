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

    @JsonProperty("current_place")
    private AccessibilityResponse current_place;

    @JsonProperty("nearby_places")
    private List<NearbyPlacesResponse> nearby_places;

    @JsonProperty("next_page_token")
    private String next_page_token;

}
