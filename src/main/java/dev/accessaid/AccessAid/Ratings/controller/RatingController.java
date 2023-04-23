package dev.accessaid.AccessAid.Ratings.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Ratings", description = "Ratings of the places")
@RestController
public class RatingController {

    @GetMapping("/rating")
    public String getRating() {
        return "Rating";
    }

    @PostMapping("/rating")
    public String postRating() {
        return "Rating";
    }

    @PutMapping("/rating/{id}")
    public String putRating() {
        return "Rating";
    }

    @DeleteMapping("/rating/{id}")
    public String deleteRating() {
        return "Rating";
    }

}
