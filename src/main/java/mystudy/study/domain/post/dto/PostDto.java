package mystudy.study.domain.post.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PostDto { // 게시글을 페이징 처리할 때 사용

    private Long postId;
    private String title;
    private LocalDateTime createdAt;
    private Integer viewCount;
    private Long memberId;  // Member
    private String username; // Member

    // queryDsl 생성자 select
    @QueryProjection
    @Builder
    public PostDto(Long postId, String title, LocalDateTime createdAt, Integer viewCount, Long memberId, String username) {
        this.postId = postId;
        this.title = title;
        this.createdAt = createdAt;
        this.viewCount = viewCount;
        this.memberId = memberId;
        this.username = username;
    }
}
