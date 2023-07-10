package kakao99.backend.release.cotroller;

import kakao99.backend.entity.Member;
import kakao99.backend.entity.Project;
import kakao99.backend.entity.ReleaseNote;
import kakao99.backend.member.service.MemberService;
import kakao99.backend.project.service.ProjectService;
import kakao99.backend.release.DTO.CreateReleaseDTO;
import kakao99.backend.release.service.ReleaseService;
import kakao99.backend.utils.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ReleaseController {
    private final ReleaseService releaseService;
    private final MemberService memberService;
    private final ProjectService projectService;
    private final ResponseMessage responseMessage;


    public ReleaseController(ReleaseService releaseService, ResponseMessage responseMessage, MemberService memberService, ProjectService projectService) {
        this.releaseService = releaseService;
        this.memberService = memberService;
        this.projectService = projectService;
        this.responseMessage = responseMessage;
    }

    @PostMapping("/release/create")
    @ResponseBody
    public ResponseEntity<ResponseMessage> createRelease(
            Authentication authentication,
            @RequestBody CreateReleaseDTO createReleaseDTO,
            @RequestParam(required = true, value = "memberId") Long memberId,
            @RequestParam(required = true, value = "projectId") Long projectId
    ) {
        // member와 project를 조회
        Member member = memberService.findMemberById(memberId);
        Project project = projectService.findProjectById(projectId);

        if (member == null || project == null) {
            ResponseMessage message = responseMessage.createMessage(404, "멤버 또는 프로젝트를 찾을 수 없습니다.", null);
            return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
        }

        ReleaseNote releaseNote = releaseService.createRelease(createReleaseDTO, member, project);

        if (releaseNote == null) {
            ResponseMessage message = responseMessage.createMessage(500, "릴리즈 생성에 실패했습니다.", null);
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ResponseMessage message = responseMessage.createMessage(200, "릴리즈 생성 완료", releaseNote);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }


    @GetMapping("/release")
    @ResponseBody
    public ResponseEntity<ResponseMessage> getReleaseNoteList (
            @RequestParam(required = true, value = "projectId") Long projectId
    ) {
        List<ReleaseNote> releaseNotesInProject = releaseService.findRelease(projectId);

        ResponseMessage message;

        if (releaseNotesInProject.isEmpty()) {
            message = responseMessage.createMessage(204, "본 프로젝트에 속한 릴리즈노트가 없습니다.", null);
            return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
        }

        message = responseMessage.createMessage(200, "릴리즈노트 목록 조회 완료", releaseNotesInProject);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/release/{releaseId}")
    @ResponseBody
    public ResponseEntity<ResponseMessage> getReleaseNoteInfo(@PathVariable Long releaseId) {

        Optional<ReleaseNote> releaseNoteInfo = releaseService.getReleaseInfo(releaseId);
        ResponseMessage message;

        if (releaseNoteInfo == null) {
            message = responseMessage.createMessage(204, "해당 릴리즈노트가 존재하지 않습니다.", null);
            return new ResponseEntity<>(message, HttpStatus.NO_CONTENT);
        }

        message = responseMessage.createMessage(200, "해당 릴리즈노트 조회 완료", releaseNoteInfo);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
