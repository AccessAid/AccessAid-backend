package dev.accessaid.AccessAid.Contact.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "contacts")
public class Contact {

    @Schema(example = "1", description = "")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(example = "name", description = "")
    @JsonProperty("name")
    private String name;

    @Schema(example = "email@email.com", description = "")
    @JsonProperty("email")
    @Email
    private String email;

    @Schema(example = "message", description = "")
    @JsonProperty("message")
    private String message;

    @Schema(example = "2022-09-10T10:00:00", description = "")
    @JsonProperty("sent_date")
    @Column(name = "sent_date")
    private LocalDateTime sentDate;

}
