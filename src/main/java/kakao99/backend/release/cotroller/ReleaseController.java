package kakao99.backend.release.cotroller;

import kakao99.backend.entity.ReleaseNote;
import kakao99.backend.release.service.ReleaseService;
import kakao99.backend.utils.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@Controller
public class ReleaseController {
    private final ReleaseService releaseService;
    private final ResponseMessage responseMessage;

    public ReleaseController(ReleaseService releaseService, ResponseMessage responseMessage) {
        this.releaseService = releaseService;
        this.responseMessage = responseMessage;
    }

    @GetMapping("/release")
    @ResponseBody
    public ResponseEntity<ResponseMessage> getReleaseNoteList (
            @RequestParam(required = true, value = "projectId") Long projectId
    ) {
        List<ReleaseNote> releaseNotesInProject = releaseService.findRelease(projectId);

        ResponseMessage message = responseMessage.createMessage(200, "릴리즈 조회 완료", releaseNotesInProject);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping
    @ResponseBody
    public ResponseEntity<ResponseMessage> getReleaseNoteInfo(
            @RequestParam(required = true, value = "releaseId") Long releaseId
    ) {
        Optional<ReleaseNote> releaseNoteInfo = releaseService.getReleaseInfo(releaseId);

        ResponseMessage message = responseMessage.createMessage(200, "릴리즈 조회 완료", releaseNoteInfo);

        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
