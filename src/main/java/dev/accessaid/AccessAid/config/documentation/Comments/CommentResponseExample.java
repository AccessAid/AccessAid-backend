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
    private Integer userId;

    @Schema(description = "ID of place related to the comment", example = "1")
    private Integer placeId;

    @Schema(description = "Id of the comment that this comment is replying to", example = "1")
    private Integer replyToComment;

    @Schema(description = "Id of the comment that has replied to this comment", example = "1")
    private Integer responseByComment;

    @Schema(description = "Whether the comment has a response", example = "false")
    private boolean hasResponse;
}
