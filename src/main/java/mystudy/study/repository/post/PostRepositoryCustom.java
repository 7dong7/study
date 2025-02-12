package mystudy.study.repository.post;

import mystudy.study.domain.dto.post.PostDto;
import mystudy.study.domain.dto.post.PostEditForm;
import mystudy.study.domain.dto.post.PostSearchCondition;
import mystudy.study.domain.dto.post.PostViewDto;
import mystudy.study.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostRepositoryCustom {

    // 전체 게시글 페이징으로 가져오기
    Page<PostDto> getPostPage(Pageable pageable, PostSearchCondition condition);

    // 사용자가 작성한 게시글 수
    Long getPostCountByMemberId(Long id);

    // 사용자가 작성한 게시글 페이징으로 가져오기
    Page<PostDto> getPostByMemberId(Long id, Pageable pageable);
    
    // 게시글 보기 - 게시글 내용 조회
    PostViewDto getPostView(Long postId);

    // 게시글 수정 - 게시글 내용 조회
    PostEditForm findByPostIdAndMemberId(Long postId, Long memberId);

    // 게시글 postId로 조회 - fetch join
    Post findByPostId(Long postId);
}
