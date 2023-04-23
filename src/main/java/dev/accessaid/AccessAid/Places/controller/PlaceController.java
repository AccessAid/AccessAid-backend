package dev.accessaid.AccessAid.Places.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.accessaid.AccessAid.Places.model.Place;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Places", description = "Information about places")
@RestController
public class PlaceController {

    @GetMapping("/places")
    public String getPlaces() {
        return "Place";
    }

    @PostMapping("/places")
    public String createPlace(@RequestBody Place place) {
        return "Place";
    }

    @PutMapping("/places/{id}")
    public String updatePlace(@RequestBody Place place) {
        return "Place";
    }

    @DeleteMapping("/places/{id}")
    public String deletePlace(@RequestBody Place place) {
        return "Place";
    }
}
