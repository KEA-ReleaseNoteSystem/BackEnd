package kakao99.backend.issue.repository;

import jakarta.persistence.EntityManager;
import kakao99.backend.entity.Issue;
import kakao99.backend.entity.Member;
import kakao99.backend.entity.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    Issue save(Issue issue);

//    Issue findByProjectIdAndIssueId(Long projectId, Long issueId);

//    @Query("select m from Issue m join fetch m.project")
//    List<Issue> findAllByProjectId(Long projectId);

    @Query("select m from Issue m join fetch m.project join fetch m.memberInCharge")
    List<Issue> findAllByProjectId(Long projectId);


    @Modifying
    @Transactional
    @Query("UPDATE Issue p SET p.title = :title, p.description =:description WHERE p.id = :issueId")
    void updateIssue(@Param("title") String title, @Param("description") String description, @Param("issueId") Long issueId);

    @Modifying
    @Transactional
    @Query("UPDATE Issue p SET p.title = :title WHERE p.id = :issueId")
    void updateIssueTitle(@Param("title") String title, @Param("issueId") Long issueId);

    @Modifying
    @Transactional
    @Query("UPDATE Issue p SET p.description = :description WHERE p.id = :issueId")
    void updateIssueDescription(@Param("description") String description, @Param("issueId") Long issueId);
}
