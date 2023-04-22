package dev.accessaid.AccessAid.Places.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Geolocation.Response.GeolocationResponse;
import dev.accessaid.AccessAid.Ratings.model.Rating;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull
    double latitude;

    @NotNull
    double longitude;

    String address;
    String api_placeId;

    @JsonIgnore
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    List<Rating> ratings;

    double totalRating;

    @JsonIgnore
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    List<Comment> comments;

    public Place(GeolocationResponse geolocationResponse) {
        this.latitude = geolocationResponse.getLatitude();
        this.longitude = geolocationResponse.getLongitude();
        this.address = geolocationResponse.getFormattedAddress();
        this.api_placeId = geolocationResponse.getPlaceId();
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
