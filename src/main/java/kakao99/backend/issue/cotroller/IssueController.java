package kakao99.backend.issue.cotroller;

import kakao99.backend.utils.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/issue")
@RequiredArgsConstructor
public class IssueController {

    private final ResponseMessage responseMessage;

    @PostMapping("/new")
    public ResponseEntity<?> createIssue() {

        ResponseMessage message = responseMessage.createMessage(200, "성공");

        return new ResponseEntity(message, HttpStatus.OK);
    }

}
