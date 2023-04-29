package dev.accessaid.AccessAid.Profile.model;

import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.config.documentation.ExamplesValues;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "profile", uniqueConstraints = { @UniqueConstraint(columnNames = { "user_id" }) })
public class Profile {

    @Schema(example = "1", description = "")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Schema(example = ExamplesValues.FIRSTNAME, description = "")
    String firstName;

    @Schema(example = ExamplesValues.LASTNAME, description = "")
    String lastName;

    @Schema(example = ExamplesValues.AVATAR_PATH, description = "")
    String avatarPath;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    User user;
}
