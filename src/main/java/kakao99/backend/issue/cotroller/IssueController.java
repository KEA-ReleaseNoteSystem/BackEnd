package kakao99.backend.issue.cotroller;

import kakao99.backend.entity.Issue;
import kakao99.backend.entity.Member;

import kakao99.backend.entity.Project;

import kakao99.backend.issue.dto.IssueDTO;


import kakao99.backend.issue.repository.IssueRepository;
import kakao99.backend.issue.service.IssueService;
import kakao99.backend.member.repository.MemberRepository;
import kakao99.backend.project.repository.ProjectRepository;
import kakao99.backend.utils.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import javax.xml.transform.OutputKeys;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class IssueController {

    private final ResponseMessage responseMessage;
    private final IssueRepository issueRepository;
    private final IssueService issueService;
    private final MemberRepository memberRepository;
    private final ProjectRepository projectRepository;

    @PostMapping("/api/{projectId}/issue/new")
    public ResponseEntity<?> createIssue(IssueForm issue, Long userId) {
        System.out.println("userId = " + userId);
        Optional<Member> memberById = memberRepository.findById(userId);

        if (memberById.isEmpty()) {
            ResponseMessage message = responseMessage.createMessage(404, "해당 userId에 해당하는 유저 데이터 없음.");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }

        Optional<Project> projectById = projectRepository.findById(issue.getProjectId());
        if (projectById.isEmpty()) {
            ResponseMessage message = responseMessage.createMessage(404, "해당 userId에 해당하는 유저 데이터 없음.");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }

        Member member = memberById.get();
        Project project = projectById.get();

        Issue newIssue = new Issue().builder()
                .title(issue.getTitle())
                .issueType(issue.getType())
                .description(issue.getDescription())
                .memberReport(member)
                .project(project)
                .build();


        issueRepository.save(newIssue);
        ResponseMessage message = responseMessage.createMessage(200, "이슈 생성 성공");

        return new ResponseEntity(message, HttpStatus.OK);
    }

    @GetMapping("api/{projectId}/issues")
    public ResponseEntity<?> getAllIssues(@PathVariable("projectId") Long projectId) {

        ArrayList<IssueDTO> allIssues = issueService.getAllIssues(projectId);

        ResponseMessage message = responseMessage.createMessage(200, projectId + "번 프로젝트의 모든 이슈 조회 성공");
        message.setData(allIssues);

        return new ResponseEntity(message, HttpStatus.OK);
    }

    @PutMapping("/api/{projectId}/issues/{issueId}")
    public ResponseEntity<?> updateIssue(@PathVariable("projectId") Long projectId, @PathVariable("issueId") Long issueId, String title, String description) {

        System.out.println("projectId = " + projectId + ", issueId = " + issueId + ", title = " + title + ", description = " + description);

        String result = issueService.updateIssue(title, description, issueId);
        ResponseMessage message = null;
        if (result == "OK") {
            message = responseMessage.createMessage(200, projectId + "번 프로젝트의 모든 이슈 수정 성공");
        }else{
            message = responseMessage.createMessage(500, projectId + "번 프로젝트의 모든 이슈 수정 실패: "+result);
        }
        return new ResponseEntity(message, HttpStatus.OK);
    }

}

