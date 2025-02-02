package mystudy.study.service.post;

import lombok.RequiredArgsConstructor;
import mystudy.study.domain.dto.post.PostEditForm;
import mystudy.study.domain.entity.Post;
import mystudy.study.repository.post.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostQueryService {

    private final PostRepository postRepository;

    // id 게시글 조회
    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow( () -> new IllegalArgumentException("해당하는 게시글이 존재하지 않습니다."));
    }

    // postId 와 memberId로 게시글 조회
    public PostEditForm findByPostIdAndMemberId(Long postId, Long memberId) {
        return postRepository.findByPostIdAndMemberId(postId, memberId);
    }
}
