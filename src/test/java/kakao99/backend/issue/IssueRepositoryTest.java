package kakao99.backend.issue;

import kakao99.backend.config.QuerydslTestConfig;
import kakao99.backend.entity.Issue;
import kakao99.backend.issue.repository.IssueRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@DataJpaTest
@Import(QuerydslTestConfig.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class IssueRepositoryTest {

    @Autowired
    private IssueRepository issueRepository;

    @Test
    void 이슈_조회() {

        Long issueId = 1L;

        Optional<Issue> issueOptional = issueRepository.findById(issueId);
        Issue issue = issueOptional.get();
        System.out.println("issue.getTitle() = " + issue.getTitle());
        assertEquals(issue.getId(), issueId);
    }


    @Test
    @DisplayName("프로젝트의 max IssueNum")
    public void getMaxIssueNum() {
        Long projectId = 1L;
        Long maxIssueNum = issueRepository.findMaxIssueNum(projectId).get();
        System.out.println("maxIssueNum = " + maxIssueNum);

    }



    @Test
    void 종료되지_않은_이슈_조회_테스트() {

        // given
        Long projectId = 1L;
        List<Issue> issueListNotFinished = issueRepository.getIssueListNotFinishedOf(projectId);    // 종료되지 않은 이슈
        int notActiveIssueListLength = issueListNotFinished.stream().toArray().length;
        System.out.println("notActiveIssueListLength = " + notActiveIssueListLength);

        // when
        List<Issue> activeIssueList = issueRepository.findAllByProjectId(projectId);
        if (activeIssueList.isEmpty()) {
            throw new NoSuchElementException("active한 issue 없음");
        }
        int activeIssueListLength = activeIssueList.stream().toArray().length;
        System.out.println("activeIssueListLength = " + activeIssueListLength);

        // then
        assertNotEquals(notActiveIssueListLength, activeIssueListLength);

    }

    @Test
    @AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
    void 이슈_생성() {

        Long newIssueId = 99L;
        Issue newIssue = new Issue().builder()
                .id(newIssueId)
                .title("테스트코드")
                .isActive(true)
                .build();
        Issue savedIssue = issueRepository.save(newIssue);

        Assertions.assertThat(newIssue.getTitle()).isEqualTo(savedIssue.getTitle());
        System.out.println("savedIssue.getTitle() = " + savedIssue.getTitle());
        System.out.println("newIssue.getTitle() = " + newIssue.getTitle());
    }


//    @Test
//    void 잔디밭_데이터_테스트() {
//        Long memberId = 1L;
//        List<IssueGrassDTO> numberOfDoneIssues = issueRepository.findNumberOfDoneIssues(memberId);
//
//        System.out.println("numberOfDoneIssues.stream().count() = " + numberOfDoneIssues.stream().count());
//        for (IssueGrassDTO grassData : numberOfDoneIssues) {
//            System.out.println("grassData.getCount() = " + grassData.getCount());
//            System.out.println("grassData.getUpdatedAt() = " + grassData.getUpdatedAt());
//            System.out.println("====");
//
//        }
//        System.out.println("numberOfDoneIssues.get(0) = " + numberOfDoneIssues.get(0).toString());
//    }
}
