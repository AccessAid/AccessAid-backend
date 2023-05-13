package dev.accessaid.AccessAid.config.documentation.Contact;

import java.time.LocalDateTime;

import dev.accessaid.AccessAid.config.documentation.ExamplesValues;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ContactResponseExample {

    @Schema(example = "1", description = "")
    private Integer id;

    @Schema(example = ExamplesValues.FIRSTNAME, description = "")
    private String name;

    @Schema(example = ExamplesValues.EMAIL, description = "")
    private String email;

    @Schema(example = ExamplesValues.MESSAGE, description = "")
    private String message;

    @Schema(example = ExamplesValues.SENT_DATE, description = "")
    private LocalDateTime sentDate;

}
