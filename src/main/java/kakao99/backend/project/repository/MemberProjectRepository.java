package kakao99.backend.project.repository;


import com.querydsl.jpa.impl.JPAUpdateClause;
import kakao99.backend.entity.MemberProject;
import kakao99.backend.entity.Project;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import kakao99.backend.entity.*;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;



@Repository
public class MemberProjectRepository{

    private final EntityManager em;
    private final JPAQueryFactory query;

    private QMember member = QMember.member;

    private QMemberProject memberProject = QMemberProject.memberProject;

    private QProject project = QProject.project;
    private QGroup group = QGroup.group;

    public MemberProjectRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public MemberProject save(MemberProject memberProject) {
        em.persist(memberProject);
        return memberProject;
    }

    public Optional<MemberProject> findAllByProjectIdAndMemberId(Long projectId, Long memberId) {
        return Optional.ofNullable(query
                .select(memberProject)
                .from(memberProject)
                .where(memberProject.project.id.eq(projectId).and(memberProject.member.id.eq(memberId)).and(memberProject.isActive.eq("true")))
                .fetchOne());
    }



    public List<MemberProject> findMemberProjectByProjectId(Long projectId) {
        memberProject = QMemberProject.memberProject;
        return query
                .select(memberProject)
                .from(memberProject)
                .where(memberProject.project.id.eq(projectId).and(memberProject.isActive.eq("true")))
                .fetch();
    }

    public List<Project> findProjectByMemberId(Long memberId, String isActive) {
        return query
                .select(project)
                .from(memberProject)
                .where(memberProject.member.id.eq(memberId)
                        .and(memberProject.isActive.eq(isActive)
                                .and(project.isActive.eq(isActive)))) // and 조건을 올바른 위치로 이동
                .fetch();
    }


    public List<Project> findOtherProject(Long memberId, Long groupId, String isActive) {
        return query
                .select(project)
                .from(project)
                .leftJoin(memberProject).on(project.id.eq(memberProject.project.id).and(memberProject.member.id.eq(memberId)))
                .where(memberProject.id.isNull() // This ensures that the project is not associated with the member
                        .and(project.isActive.eq(isActive)
                                .and(project.group.id.eq(groupId))))
                .fetch();
    }


    public void deleteMemberProjectByProjectId(Long projectId) {
        Date currentTime = new Date();
        new JPAUpdateClause(em, memberProject)
                .set(memberProject.isActive, "false")
                .set(memberProject.deletedAt, currentTime)
                .where(memberProject.project.id.eq(projectId))
                .execute();
    }

    public Optional<Member> findPMByProjectId(Long projectId, String role) {
        return Optional.ofNullable(query
                .select(memberProject.member)
                .from(memberProject)
                .where(memberProject.project.id.eq(projectId)
                        .and(memberProject.role.eq(role)))
                .fetchFirst());
    }

    public Optional<String> role(Long projectId, Long memberId) {
        return Optional.ofNullable(query
                .select(memberProject.role)
                .from(memberProject)
                .where(memberProject.member.id.eq(memberId)
                        .and(memberProject.project.id.eq(projectId)))
                .fetchOne());
    }

    public void updateRole(Long pmId, Long memberId, Long projectId) {
        new JPAUpdateClause(em, memberProject)
                .where(memberProject.member.id.eq(pmId).and(memberProject.project.id.eq(projectId)))
                .set(memberProject.role, "Member")
                .execute();

        new JPAUpdateClause(em, memberProject)
                .where(memberProject.member.id.eq(memberId).and(memberProject.project.id.eq(projectId)))
                .set(memberProject.role, "PM")
                .execute();
    }

}
