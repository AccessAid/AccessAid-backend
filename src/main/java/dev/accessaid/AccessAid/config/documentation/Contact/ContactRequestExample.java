package dev.accessaid.AccessAid.config.documentation.Contact;

import dev.accessaid.AccessAid.config.documentation.ExamplesValues;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class ContactRequestExample {

    @Schema(example = ExamplesValues.FIRSTNAME, description = "")
    String name;

    @Schema(example = ExamplesValues.EMAIL, description = "")
    String email;

    @Schema(example = ExamplesValues.SUBJECT, description = "")
    String subject;

    @Schema(example = ExamplesValues.MESSAGE, description = "")
    String message;
}
