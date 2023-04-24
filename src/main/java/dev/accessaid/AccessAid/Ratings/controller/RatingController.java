package dev.accessaid.AccessAid.Ratings.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.accessaid.AccessAid.Geolocation.Response.ErrorResponse;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Places.service.PlaceServiceImpl;
import dev.accessaid.AccessAid.Ratings.exceptions.RatingNotFoundException;
import dev.accessaid.AccessAid.Ratings.exceptions.RatingSaveException;
import dev.accessaid.AccessAid.Ratings.model.Rating;
import dev.accessaid.AccessAid.Ratings.response.RatingResponse;
import dev.accessaid.AccessAid.Ratings.service.RatingServiceImpl;
import dev.accessaid.AccessAid.Ratings.utils.RatingMapper;
import dev.accessaid.AccessAid.model.User;
import dev.accessaid.AccessAid.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Ratings", description = "Ratings of the places")
@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingServiceImpl ratingService;

    @Autowired
    private UserService userService;

    @Autowired
    private PlaceServiceImpl placeService;

    @GetMapping("")
    public ResponseEntity<?> seeAllRatings() {
        try {
            List<Rating> ratings = ratingService.getAllRatings();
            List<RatingResponse> responses = RatingMapper.toRatingResponses(ratings);
            return ResponseEntity.ok(responses);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> seeRatingById(@PathVariable Integer id) {
        try {
            Rating rating = ratingService.getRatingById(id);
            RatingResponse response = RatingMapper.toRatingResponse(rating);
            return ResponseEntity.ok(response);
        } catch (RatingNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    @PostMapping("")
    public ResponseEntity<?> addRating(@RequestBody Rating rating) {
        try {
            Rating newRating = ratingService.createRating(rating);
            RatingResponse response = RatingMapper.toRatingResponse(newRating);
            return ResponseEntity.ok(response);
        } catch (RatingSaveException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRating(@PathVariable Integer id, @RequestBody Rating rating) {
        try {
            Rating ratingToUpdate = ratingService.getRatingById(id);
            if (ratingToUpdate == null) {
                ErrorResponse errorResponse = new ErrorResponse("Rating not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
            ratingToUpdate.setRating(rating.getRating());
            Rating udpatedRating = ratingService.changeRating(ratingToUpdate);
            RatingResponse response = RatingMapper.toRatingResponse(udpatedRating);
            return ResponseEntity.ok(response);
        } catch (RatingNotFoundException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);

        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRating(@PathVariable Integer id) {
        try {
            Rating ratingToDelete = ratingService.getRatingById(id);
            if (ratingToDelete == null) {
                ErrorResponse errorResponse = new ErrorResponse("Rating not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
            ratingService.removeRating(id);
            RatingResponse response = RatingMapper.toRatingResponse(ratingToDelete);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> seeRatingsByUser(@PathVariable Integer userId) {
        try {
            User user = userService.getUserById(userId);
            if (user == null) {
                ErrorResponse errorResponse = new ErrorResponse("User not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
            List<Rating> ratings = ratingService.getRatingByUser(user);
            if (ratings.isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("User has not rated any place");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
            List<RatingResponse> response = RatingMapper.toRatingResponses(ratings);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }

    @GetMapping("/place/{placeId}")
    public ResponseEntity<?> seeRatingsByPlace(@PathVariable Integer placeId) {
        try {
            Place place = placeService.findPlaceById(placeId);
            if (place == null) {
                ErrorResponse errorResponse = new ErrorResponse("Place not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
            List<Rating> places = ratingService.getRatingByPlace(place);
            if (places.isEmpty()) {
                ErrorResponse errorResponse = new ErrorResponse("Place has not been rated by anyone");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
            }
            List<RatingResponse> response = RatingMapper.toRatingResponses(places);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }

    }
}