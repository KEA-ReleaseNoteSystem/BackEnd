package kakao99.backend.issue.repository;

import io.lettuce.core.dynamic.annotation.Param;
import kakao99.backend.entity.Issue;
import kakao99.backend.entity.IssueParentChild;
import kakao99.backend.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IssueParentChildRepositiory extends JpaRepository <IssueParentChild, Long> {
    IssueParentChild save(IssueParentChild issue);

    List<IssueParentChild> findByparentIssue(Issue parentIssue);
    List<IssueParentChild> findBychildIssue(Issue childIssue);

    boolean existsByChildIssue(Issue issue);
}

