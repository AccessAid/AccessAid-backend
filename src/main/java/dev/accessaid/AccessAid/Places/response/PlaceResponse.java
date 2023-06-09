package dev.accessaid.AccessAid.Places.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import dev.accessaid.AccessAid.Accessibility.response.AccessibilityResponse;
import dev.accessaid.AccessAid.Geolocation.Response.GeolocationResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@JsonPropertyOrder({ "id", "latitude", "longitude", "address", "apiPlaceId", "totalRating", "accessibilityData" })
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlaceResponse extends GeolocationResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("totalRating")
    private double totalRating;

    @JsonProperty("accessibilityData")
    private AccessibilityResponse accessibilityData;

    public PlaceResponse(Integer id, double latitude, double longitude, String address,
            String apiPlaceId, double totalRating, AccessibilityResponse accessibility) {
        super(latitude, longitude, address, apiPlaceId);
        this.id = id;
        this.totalRating = totalRating;
        this.accessibilityData = accessibility;
    }

}
