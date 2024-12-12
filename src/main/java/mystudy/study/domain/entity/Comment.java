package mystudy.study.domain.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import mystudy.study.domain.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class Comment extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private String content;


    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post; // 게시글 ID


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member author; // 작성자 ID


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private Comment parentComment; // 부모 댓글 ID


    @OneToMany(mappedBy = "parentComment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replies = new ArrayList<>(); // 부모 댓글의 댓글들


}