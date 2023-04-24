package dev.accessaid.AccessAid.Places.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.accessaid.AccessAid.Geolocation.Response.GeolocationResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlaceResponse extends GeolocationResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("totalRating")
    private double totalRating;

    public PlaceResponse(Integer id, double latitude, double longitude, String address,
            String api_placeId, double totalRating) {
        super(latitude, longitude, address, api_placeId);
        this.id = id;
        this.totalRating = totalRating;
    }
}
