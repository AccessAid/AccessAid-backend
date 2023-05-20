package dev.accessaid.AccessAid.Accessibility.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.maps.model.AddressType;
import com.google.maps.model.OpeningHours;
import com.google.maps.model.Photo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessibilityResponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("url")
    private String url;

    @JsonProperty("accessibility")
    private Boolean wheelchairAccessibleEntrance;

    @JsonProperty("openingHours")
    private OpeningHours openingHours;

    @JsonProperty("website")
    private String website;

    @JsonProperty("types")
    private AddressType[] types;

    @JsonProperty("photos")
    private Photo[] photos;

}
