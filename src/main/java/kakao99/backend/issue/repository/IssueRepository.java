package kakao99.backend.issue.repository;

import kakao99.backend.entity.Issue;
import kakao99.backend.entity.Member;
import kakao99.backend.entity.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    Issue save(Issue issue);

    List<Issue> findByProjectId(Long projectId);

    @Query("select m from Issue m join fetch m.project")
    List<Issue> findAllByProjectId(Long projectId);

//    @Override // (1)
//    @EntityGraph(attributePaths = "member") // (2)
//    List<Issue> findAllMember();

////    @Override // (1)
//    @EntityGraph(attributePaths = "project") // (2)
//    List<Issue> findAllIssueWithProject();

}
