package dev.accessaid.AccessAid.Ratings.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Places.service.PlaceServiceImpl;
import dev.accessaid.AccessAid.Ratings.exceptions.RatingNotFoundException;
import dev.accessaid.AccessAid.Ratings.model.Rating;
import dev.accessaid.AccessAid.Ratings.response.RatingResponse;
import dev.accessaid.AccessAid.Ratings.service.RatingServiceImpl;
import dev.accessaid.AccessAid.Ratings.utils.RatingMapper;
import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.User.service.UserService;
import dev.accessaid.AccessAid.config.documentation.Ratings.RatingRequestExample;
import dev.accessaid.AccessAid.config.documentation.Ratings.RatingResponseExample;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "See a list of ratings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RatingResponseExample.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("")
    public List<RatingResponse> seeAllRatings() {
        List<Rating> ratings = ratingService.getAllRatings();
        return RatingMapper.toRatingResponses(ratings);

    }

    @Operation(summary = "See a list of ratings")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RatingResponseExample.class)))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/paged")
    public Page<RatingResponse> seeAllRatings(@Parameter(hidden = true) Pageable pageable) {
        Page<Rating> ratings = ratingService.getAllRatings(pageable);
        return RatingMapper.toRatingResponses(ratings, pageable);

    }

    @Operation(summary = "See a rating by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = RatingResponseExample.class))),
            @ApiResponse(responseCode = "404", description = "Rating not found", content = @Content)
    })
    @GetMapping("/{id}")
    public RatingResponse seeRatingById(@PathVariable Integer id) {
        Rating rating = ratingService.getRatingById(id);
        return RatingMapper.toRatingResponse(rating);

    }

    @Operation(summary = "Add a new rating for a place")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Rating added successfully", content = @Content(schema = @Schema(implementation = RatingResponseExample.class))),
            @ApiResponse(responseCode = "400", description = "Error saving rating", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public RatingResponse addRating(
            @RequestBody @Validated @Schema(implementation = RatingRequestExample.class) Rating rating) {
        Rating newRating = ratingService.createRating(rating);
        return RatingMapper.toRatingResponse(newRating);

    }

    @Operation(summary = "Update an existing rating")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rating updated successfully", content = @Content(schema = @Schema(implementation = RatingResponseExample.class))),
            @ApiResponse(responseCode = "404", description = "Rating not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping("/{id}")
    public RatingResponse updateRating(@PathVariable Integer id,
            @RequestBody @Schema(example = "{\"rating\": 5.0}") Rating rating) {
        Rating ratingToUpdate = ratingService.getRatingById(id);
        if (ratingToUpdate == null) {
            throw new RatingNotFoundException("Rating not found");
        }
        ratingToUpdate.setRating(rating.getRating());
        Rating udpatedRating = ratingService.changeRating(ratingToUpdate);
        return RatingMapper.toRatingResponse(udpatedRating);

    }

    @Operation(summary = "Delete a rating by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted successfully", content = @Content),
            @ApiResponse(responseCode = "404", description = "Rating not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRating(@PathVariable Integer id) {

        ratingService.removeRating(id);
    }

    @Operation(summary = "See all ratings that a user has made")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RatingResponseExample.class)))),
            @ApiResponse(responseCode = "404", description = "Ratings not found", content = @Content)
    })
    @GetMapping("/user/{userId}")
    public List<RatingResponse> seeRatingsByUser(@PathVariable Integer userId) {
        User user = userService.getUserById(userId);
        List<Rating> ratings = ratingService.getRatingByUser(user);
        return RatingMapper.toRatingResponses(ratings);

    }

    @Operation(summary = "See all ratings that a user has made")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RatingResponseExample.class)))),
            @ApiResponse(responseCode = "404", description = "Ratings not found", content = @Content)
    })
    @GetMapping("/user/{userId}/paged")
    public Page<RatingResponse> seeRatingsByUser(@PathVariable Integer userId,
            @Parameter(hidden = true) Pageable pageable) {
        User user = userService.getUserById(userId);
        Page<Rating> ratings = ratingService.getRatingByUser(user, pageable);
        return RatingMapper.toRatingResponses(ratings, pageable);

    }

    @Operation(summary = "See all ratings that have been made for a place")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RatingResponseExample.class)))),
            @ApiResponse(responseCode = "404", description = "Ratings not found", content = @Content)
    })
    @GetMapping("/place/{placeId}")
    public List<RatingResponse> seeRatingsByPlace(@PathVariable Integer placeId) {
        Place place = placeService.findPlaceById(placeId);
        List<Rating> places = ratingService.getRatingByPlace(place);
        return RatingMapper.toRatingResponses(places);

    }

    @Operation(summary = "See all ratings that have been made for a place")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(array = @ArraySchema(schema = @Schema(implementation = RatingResponseExample.class)))),
            @ApiResponse(responseCode = "404", description = "Ratings not found", content = @Content)
    })
    @GetMapping("/place/{placeId}/paged")
    public Page<RatingResponse> seeRatingsByPlace(@PathVariable Integer placeId,
            @Parameter(hidden = true) Pageable pageable) {
        Place place = placeService.findPlaceById(placeId);
        Page<Rating> places = ratingService.getRatingByPlace(place, pageable);
        return RatingMapper.toRatingResponses(places, pageable);

    }

}