package dev.accessaid.AccessAid.Places.model;

import java.util.List;

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Ratings.model.Rating;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    double latitude;
    double longitude;
    String address;
    String api_placeId;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    List<Rating> ratings;
    double totalRating;
    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    List<Comment> comments;

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
        double sum = 0.0;
        for (Rating rating : ratings) {
            sum += rating.getRating();
        }
        return sum / ratings.size();
    }

}
