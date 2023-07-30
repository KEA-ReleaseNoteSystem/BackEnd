package kakao99.backend.issue.dto;

import kakao99.backend.entity.Issue;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Setter
public class GPTQuestionDTO {
    private Long id;
    private String question;
    private String importance;
    private String requestToGPT;

    public static List<GPTQuestionDTO> organizeIssueListIntoQuestion(List<Issue> issueListNotFinished) {
        List<GPTQuestionDTO> questionList = issueListNotFinished.stream().map(issue ->
                GPTQuestionDTO.builder()
                        .id(issue.getId())
                        .question(issue.getTitle())
                        .requestToGPT("위의 질문들에 대한 각각의 중요도 알려줘.")
                        .build()
        ).collect(Collectors.toList());
        return questionList;
    }
}
