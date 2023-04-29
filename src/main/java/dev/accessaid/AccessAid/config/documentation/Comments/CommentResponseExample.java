package dev.accessaid.AccessAid.config.documentation.Comments;

import dev.accessaid.AccessAid.config.documentation.ExamplesValues;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CommentResponseExample {

    @Schema(description = "Unique ID of the comment", example = "1")
    private Integer id;

    @Schema(description = "Comment from a user", example = ExamplesValues.COMMENT)
    private String comment;

    @Schema(description = "ID of user who made the comment", example = "1")
    private Integer user_id;

    @Schema(description = "ID of place related to the comment", example = "1")
    private Integer place_id;

}
