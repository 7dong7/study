package mystudy.study.repository;

import jakarta.persistence.EntityManager;
import mystudy.study.domain.entity.Member;
import mystudy.study.domain.entity.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    PostRepository postRepository;
    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        // 사용자 등록
        Member member1 = new Member("member1", 10, "member1@naver.com");

        // 글 작성
        Post post1 = new Post("새로운 글작성1", "새로운 글이 작성되었습니다1.", member1);
        Post post2 = new Post("새로운 글작성2", "새로운 글이 작성되었습니다2.", member1);

        member1.addPost(post1);
        member1.addPost(post2);

        memberRepository.save(member1);
    }

    @Test
    public void postBasicTest() throws Exception{
        // member 찾아보기
        Optional<Member> optMember = memberRepository.findByUsername("member1");

        Member member = optMember.orElseThrow(() -> new IllegalArgumentException("사용자가 없습니다"));

        List<Post> posts = member.getPosts();

        for (Post post : posts) {
            System.out.println("post = " + post);
            System.out.println("post.getCreatedAt() = " + post.getCreatedAt());
            System.out.println("post.getUpdatedBy() = " + post.getUpdatedAt());
            System.out.println("post.getCreatedBy() = " + post.getCreatedBy());
            System.out.println("post.getUpdatedBy() = " + post.getUpdatedBy());
        }

        // 삭제
        member.removePost(member.getPosts().get(0));
        Optional<Member> optMember2 = memberRepository.findByUsername("member1");

        Member member2 = optMember.orElseThrow(() -> new IllegalArgumentException("사용자가 없습니다"));
        List<Post> posts2 = member2.getPosts();

        for (Post post : posts2) {
            System.out.println("post = " + post);
            System.out.println("post.getCreatedAt() = " + post.getCreatedAt());
            System.out.println("post.getUpdatedBy() = " + post.getUpdatedAt());
            System.out.println("post.getCreatedBy() = " + post.getCreatedBy());
            System.out.println("post.getUpdatedBy() = " + post.getUpdatedBy());
        }
    }
}