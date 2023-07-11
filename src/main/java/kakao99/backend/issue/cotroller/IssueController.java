package kakao99.backend.issue.cotroller;

import kakao99.backend.entity.Issue;
import kakao99.backend.entity.Member;
import kakao99.backend.issue.dto.IssueDTO;
import kakao99.backend.issue.repository.IssueRepository;
import kakao99.backend.member.repository.MemberRepository;
import kakao99.backend.utils.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class IssueController {

    private final ResponseMessage responseMessage;
    private final IssueRepository issueRepository;
    private final MemberRepository memberRepository;

    @PostMapping("/api/{projectId}/issue/new")
    public ResponseEntity<?> createIssue(IssueForm issue, Long userId) {
        System.out.println("userId = " + userId);
        Optional<Member> byId = memberRepository.findById(userId);

        if (byId.isEmpty()) {
            ResponseMessage message = responseMessage.createMessage(404, "해당 userId에 해당하는 유저 데이터 없음.");
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }
        Member member = byId.get();
        Issue newIssue = new Issue().builder()
                .title(issue.getTitle())
                .issueType(issue.getType())
                .description(issue.getDescription())
                .memberReport(member)
                .build();

        issueRepository.save(newIssue);
        ResponseMessage message = responseMessage.createMessage(200, "이슈 생성 성공");

        return new ResponseEntity(message, HttpStatus.OK);
    }


    @GetMapping("api/{projectId}/issues")
    public ResponseEntity<?> getAllIssues(@PathVariable("projectId") Long projectId) {

        List<Issue> allIssueByProjectId = issueRepository.findAllByProjectId(projectId);
        ArrayList<IssueDTO> issueDTOList = new ArrayList<>();
        for (Issue issue : allIssueByProjectId) {
//            Member memberInCharge = issue.getMemberInCharge();
//            System.out.println("memberInCharge = " + memberInCharge.getNickname());

            IssueDTO issueDTO = new IssueDTO().builder()
                    .id(issue.getId())
                    .issueNum(issue.getIssueNum())
                    .title(issue.getTitle())
                    .issueType(issue.getIssueType())
                    .description(issue.getDescription())
                    .status(issue.getStatus())
                    .file(issue.getFile())
                    .createdAt(issue.getCreatedAt())
                    .memberIdInCharge(issue.getMemberInCharge())
//                    .memberIdReport(issue.getMemberReport().getId())
                    .build();

            issueDTOList.add(issueDTO);
        }
        ResponseMessage message = responseMessage.createMessage(200, projectId+"번 프로젝트의 모든 이슈 조회 성공");
        message.setData(issueDTOList);

        return new ResponseEntity(message, HttpStatus.OK);
    }
}

