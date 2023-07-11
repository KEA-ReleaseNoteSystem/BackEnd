package kakao99.backend.project.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kakao99.backend.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberProjectRepository{

    private final EntityManager em;
    private final JPAQueryFactory query;

    private QMember member = QMember.member;

    private QMemberProject memberProject = QMemberProject.memberProject;

    private QProject project = QProject.project;

    public MemberProjectRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public List<Member> findMemberByProjectId(Long projectId) {
        return query
                .select(member)
                .from(memberProject)
                .where(memberProject.project.id.eq(projectId))
                .fetch();
    }

    public List<Project> findProjectByMemberId(Long memberId) {
        return query
                .select(project)
                .from(memberProject)
                .where(memberProject.member.id.eq(memberId))
                .fetch();
    }

}
