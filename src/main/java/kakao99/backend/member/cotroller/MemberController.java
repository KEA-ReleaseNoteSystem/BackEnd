package kakao99.backend.member.cotroller;

import kakao99.backend.member.dto.LoginDTO;
import kakao99.backend.member.dto.RegisterDTO;
import kakao99.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member/signup")
    public ResponseEntity<?> register(@Validated @RequestBody RegisterDTO registerDTO) {

        return memberService.join(registerDTO);

    }

    @PostMapping("/member/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginDTO loginDTO) {

        return memberService.login(loginDTO);
    }
}
