package kakao99.backend.issue.repository;

import kakao99.backend.entity.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    Issue save(Issue issue);


    @Query("select m from Issue m join fetch m.project")
    List<Issue> findAllByProjectId(Long projectId);


}
