package mystudy.study.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import mystudy.study.domain.entity.QComment;

import static com.querydsl.jpa.JPAExpressions.select;
import static mystudy.study.domain.entity.QComment.comment;

public class CommentRepositoryImpl implements CommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public Long getCommentCountByMemberId(Long id) {

        return queryFactory
                .select(comment.count())
                .from(comment)
                .where(comment.member.id.eq(id))
                .fetchOne();
    }
}
