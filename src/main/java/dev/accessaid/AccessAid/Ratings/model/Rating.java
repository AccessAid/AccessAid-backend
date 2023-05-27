package dev.accessaid.AccessAid.Ratings.model;

import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.User.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
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
@Table(name = "rating", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id", "place_id" }) })
public class Rating {

    @Schema(example = "1", description = "")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Schema(example = "4.5", description = "")
    @NotNull(message = "must not be null")
    @DecimalMin(value = "0.25", message = "should be between zero and five")
    @DecimalMax(value = "5", message = "should be between one and five")
    Double rating;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "place_id")
    Place place;

}
