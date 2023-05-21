package dev.accessaid.AccessAid.Geolocation.Response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GeolocationResponse {
    private double latitude;
    private double longitude;
    private String formattedAddress;
    private String placeId;

    @JsonProperty("lat")
    public double getLatitude() {
        return latitude;
    }

    @JsonProperty("lng")
    public double getLongitude() {
        return longitude;
    }

    @JsonProperty("formattedAddress")
    public String getFormattedAddress() {
        return formattedAddress;
    }

    @JsonProperty("placeId")
    public String getPlaceId() {
        return placeId;
    }

}