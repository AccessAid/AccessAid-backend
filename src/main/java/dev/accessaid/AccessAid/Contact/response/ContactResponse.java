package dev.accessaid.AccessAid.Contact.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import dev.accessaid.AccessAid.config.documentation.ExamplesValues;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContactResponse {

    @Schema(example = "1", description = "")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Schema(example = ExamplesValues.FIRSTNAME, description = "")
    @JsonProperty("name")
    private String name;

    @Schema(example = ExamplesValues.EMAIL, description = "")
    @JsonProperty("email")
    private String email;

    @Schema(example = ExamplesValues.MESSAGE, description = "")
    @JsonProperty("message")
    private String message;

    @Schema(example = ExamplesValues.SENT_DATE, description = "")
    @JsonProperty("sent_date")
    private LocalDateTime sentDate;

}
