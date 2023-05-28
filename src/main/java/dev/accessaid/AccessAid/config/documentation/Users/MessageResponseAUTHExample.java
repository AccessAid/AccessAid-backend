package dev.accessaid.AccessAid.config.documentation.Users;

import dev.accessaid.AccessAid.config.documentation.ExamplesValues;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class MessageResponseAUTHExample {

    @Schema(example = ExamplesValues.REGISTER_MESSAGE)
    private String message;
}
