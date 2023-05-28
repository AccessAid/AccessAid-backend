package dev.accessaid.AccessAid.Comments.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("comment")
    private String comment;

    @JsonProperty("userId")
    private Integer user_id;

    @JsonProperty("placeId")
    private Integer place_id;

    @JsonProperty("replyToCommentId")
    private Integer replyToComment_id;

    @JsonProperty("responseByCommentId")
    private Integer responseByComment_id;

    @JsonProperty("hasResponse")
    private boolean hasResponse;
}
