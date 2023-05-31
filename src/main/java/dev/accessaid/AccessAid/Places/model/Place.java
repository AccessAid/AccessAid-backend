package dev.accessaid.AccessAid.Places.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Geolocation.Response.GeolocationResponse;
import dev.accessaid.AccessAid.Ratings.model.Rating;
import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.config.documentation.ExamplesValues;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "place")
public class Place {

    @Schema(example = "1", description = "")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Schema(example = ExamplesValues.LATITUDE, description = "")
    double latitude;

    @Schema(example = ExamplesValues.LONGITUDE, description = "")
    double longitude;

    @Schema(example = ExamplesValues.ADDRESS, description = "")
    String address;

    @Schema(example = ExamplesValues.API_PLACE_ID, description = "")
    String apiPlaceId;

    @JsonIgnore
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    List<Rating> ratings;

    @Schema(example = "4.5", description = "")
    double totalRating;

    @JsonIgnore
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    List<Comment> comments;

    @ManyToMany
    @JoinTable(name = "user_place", joinColumns = {
            @JoinColumn(name = "place_id") }, inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private List<User> users;

    public Place(GeolocationResponse geolocationResponse) {
        this.latitude = geolocationResponse.getLatitude();
        this.longitude = geolocationResponse.getLongitude();
        this.address = geolocationResponse.getFormattedAddress();
        this.apiPlaceId = geolocationResponse.getPlaceId();
        this.ratings = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.totalRating = 0.0;
        this.users = new ArrayList<>();
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPlace(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setPlace(null);
    }

    public void addRating(Rating rating) {
        ratings.add(rating);
        rating.setPlace(this);
    }

    public void removeRating(Rating rating) {
        ratings.remove(rating);
        rating.setPlace(null);
    }

    public double getTotalRating() {
        if (ratings.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Rating rating : ratings) {
            sum += rating.getRating();
        }
        return sum / ratings.size();
    }
}
