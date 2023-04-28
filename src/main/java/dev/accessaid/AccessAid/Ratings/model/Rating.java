package dev.accessaid.AccessAid.Ratings.model;

import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.model.User;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @NotNull
    @Min(1)
    @Max(5)
    Double rating;

    @ManyToOne
    @JoinColumn(name = "user_id")
    User user;

    @ManyToOne
    @JoinColumn(name = "place_id")
    Place place;

}
