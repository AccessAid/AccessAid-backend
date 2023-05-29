package dev.accessaid.AccessAid.User.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.Profile.model.Profile;
import dev.accessaid.AccessAid.Ratings.model.Rating;
import dev.accessaid.AccessAid.security.model.RefreshToken;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User {

    @Schema(example = "1", description = "")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(example = "username", description = "")
    @JsonProperty("username")
    @NotNull(message = "username is required")
    private String username;

    @Schema(example = "email@email.com", description = "")
    @JsonProperty("email")
    @NotNull(message = "email is required")
    @Email
    private String email;

    @Schema(example = "password", description = "")
    @NotNull(message = "password is required")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> comments;

    @ManyToMany(mappedBy = "users")
    private List<Place> places;

    @ManyToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Profile profile;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<RefreshToken> refreshTokens;

    public void updateFields(User updatedUser) {

        if (updatedUser.getEmail() != null) {
            this.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getUsername() != null) {
            this.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getPassword() != null) {
            this.setPassword(updatedUser.getPassword());
        }
    }
}
