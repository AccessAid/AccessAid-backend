package dev.accessaid.AccessAid.config.documentation.Ratings;

import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.User.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RatingRequestExample {

    @Schema(example = "4.5", description = "")
    @NotNull
    @DecimalMin("0.25")
    @DecimalMax("5")
    Double rating;

    @ManyToOne
    @JoinColumn(name = "userId")
    User user;

    @ManyToOne
    @JoinColumn(name = "placeId")
    Place place;
}
