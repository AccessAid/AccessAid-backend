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

    @JsonProperty("user_id")
    private Integer user_id;

    @JsonProperty("place_id")
    private Integer place_id;

}
