package kakao99.backend.member.service;

import kakao99.backend.entity.Member;
import kakao99.backend.jwt.TokenProvider;
import kakao99.backend.member.dto.LoginDTO;
import kakao99.backend.member.dto.RegisterDTO;
import kakao99.backend.member.repository.MemberRepository;
import kakao99.backend.utils.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final ResponseMessage responseMessage;

    @Transactional
    public ResponseEntity<?> join(RegisterDTO registerDTO) {

        Optional<Member> findEmailMember = memberRepository.findByEmail(registerDTO.getEmail());

        if (findEmailMember.isPresent()) {
            ResponseMessage message = responseMessage.createMessage(HttpStatus.BAD_REQUEST, "이미 가입된 이메일 입니다.");
            return new ResponseEntity<>(message, HttpStatus.OK);
        }

        Member member = Member.builder()
                .username(registerDTO.getName())
                .nickname(registerDTO.getNickname())
                .email(registerDTO.getEmail())
                .password(passwordEncoder.encode(registerDTO.getPassword()))
                .createdAt(LocalDate.now())
                .position(registerDTO.getPosition())
                .isActive(false)
                .build();

        memberRepository.save(member);
        ResponseMessage message = responseMessage.createMessage(HttpStatus.OK,"로그인이 완료 되었습니다.");
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> login(LoginDTO loginDTO) {

        Optional<Member> byEmail = memberRepository.findByEmail(loginDTO.getEmail());

        if (byEmail.isEmpty()) {
            return new ResponseEntity<>("존재 하지 않는 이메일입니다.", HttpStatus.NOT_FOUND);
        }

        Member member = byEmail.get();

        if (!checkPassword(loginDTO.getPassword(), member.getPassword())) {
            return new ResponseEntity<>("비밀번호가 일치 하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        String accessToken = tokenProvider.createAccessToken(member);
        ResponseMessage message = responseMessage.createMessage(HttpStatus.OK, "로그인이 완료 되었습니다.", accessToken);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    Boolean checkPassword(String rawPassword, String encodePassword) {

        return passwordEncoder.matches(rawPassword, encodePassword);
    }
}
