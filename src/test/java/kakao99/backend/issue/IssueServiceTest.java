package kakao99.backend.issue;

import kakao99.backend.BackendApplication;
import kakao99.backend.config.TestConfig;
import kakao99.backend.entity.Issue;
import kakao99.backend.issue.repository.IssueRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ContextConfiguration(classes = BackendApplication.class)
//@Import(TestConfig.class)
public class IssueServiceTest {

//    @Autowired
//    IssueServiceTest issueService;

    @Autowired
    IssueRepository issueRepository;

//    @BeforeEach
//    public void beforeEach() {
//    issueRepository = new IssueRepository() {
//    }
//    }

    @Test
    void Chat_GPT_API_테스트() {
        System.out.println("hi");

//        issueRepository.getIssueListNotFinishedOf(projectId);
    }

    @Test
    void 이슈_조회() {

        Long issueId = 1L;

        Optional<Issue> issueOptional = issueRepository.findById(issueId);
        Issue issue = issueOptional.get();
        System.out.println("issue.getTitle() = " + issue.getTitle());
        assertEquals(issue.getId(), issueId);
    }


    @Test
    void 종료되지_않은_이슈_조회_테스트() {

        // given
        Long projectId = 1L;
        List<Issue> issueListNotFinished = issueRepository.getIssueListNotFinishedOf(projectId);    // 종료되지 않은 이슈
//        System.out.println("issueListNotFinished = " + issueListNotFinished);
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

        assertThat(activeIssueListLength, greaterThan(notActiveIssueListLength));
//        assertThat(notActiveIssueListLength, greaterThan(activeIssueListLength));

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
        issueRepository.save(newIssue);

        Optional<Issue> issueById = issueRepository.findById(99L);
        System.out.println("issueById.get().getTitle() = " + issueById.get().getTitle());

//        assertEquals(issueById.get().getId(), newIssueId);

    }

}
