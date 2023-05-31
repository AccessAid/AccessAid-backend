package dev.accessaid.AccessAid.Comments.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import dev.accessaid.AccessAid.Places.model.Place;
import dev.accessaid.AccessAid.User.model.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
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
    @NotNull(message = "must not be null")
    String comment;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "place_id", referencedColumnName = "id")
    private Place place;

    @OneToOne
    @JoinColumn(name = "reply_to_comment_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Comment replyToComment;

    @OneToOne
    @JoinColumn(name = "replied_comment_id", referencedColumnName = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Comment repliedComment;

    @Column(name = "has_response")
    private boolean hasResponse;

}
