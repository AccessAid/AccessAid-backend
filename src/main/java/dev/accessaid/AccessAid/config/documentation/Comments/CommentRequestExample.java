package dev.accessaid.AccessAid.config.documentation.Comments;

import dev.accessaid.AccessAid.Comments.model.Comment;
import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.User.model.User;
import dev.accessaid.AccessAid.config.documentation.ExamplesValues;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CommentRequestExample {

    @Schema(example = ExamplesValues.COMMENT, description = "")
    @NotNull
    String comment;

    @Schema(description = "user", example = "{\"id\":1}")
    @ManyToOne
    @JoinColumn(name = "userId")
    User user;

    @Schema(description = "place", example = "{\"id\":1}")
    @ManyToOne
    @JoinColumn(name = "placeId")
    Place place;

    @Schema(description = "replyToComment", example = "{\"id\":1}")
    @OneToOne
    @JoinColumn(name = "replyToComment")
    Comment replyToComment;
}
