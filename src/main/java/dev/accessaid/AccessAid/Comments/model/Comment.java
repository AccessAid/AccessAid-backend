package dev.accessaid.AccessAid.Comments.model;

import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.User.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comment")
public class Comment {

    @Schema(example = "1", description = "")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Schema(description = "Comment", example = "Nice place!")
    @NotNull(message="must not be null")
    String comment;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Place place;

    @OneToOne
    @JoinColumn(name = "reply_to_comment_id", referencedColumnName = "id")
    private Comment replyToComment;

    @OneToOne
    @JoinColumn(name = "replied_comment_id", referencedColumnName = "id")
    private Comment repliedComment;

    @Column(name = "has_response")
    private boolean hasResponse;

}
