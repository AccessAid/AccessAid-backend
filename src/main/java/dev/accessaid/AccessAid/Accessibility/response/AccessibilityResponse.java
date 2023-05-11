package dev.accessaid.AccessAid.Accessibility.response;

import java.net.URL;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.maps.model.AddressType;
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

    @JsonProperty("url")
    private URL url;

    @JsonProperty("accessibility")
    private Boolean wheelchair_accessible_entrance;

    @JsonProperty("website")
    private URL website;

    @JsonProperty("types")
    private AddressType[] types;

    @JsonProperty("photos")
    private Photo[] photos;

}
