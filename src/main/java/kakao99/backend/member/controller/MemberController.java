package kakao99.backend.member.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import kakao99.backend.entity.Member;
import kakao99.backend.group.dto.GroupNameDTO;
import kakao99.backend.member.dto.*;
import kakao99.backend.member.service.MemberService;
import kakao99.backend.common.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static io.lettuce.core.pubsub.PubSubOutput.Type.message;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private static final Set<HttpSession> activeSessions = new HashSet<>();
    private final RedisTemplate<String, String> redisTemplate;

    @PostMapping("/api/member/signup/group")
    public ResponseEntity<?> groupCreate(@Validated @RequestBody RegisterDTO registerDTO) {
        memberService.create(registerDTO);
        ResponseMessage message = new ResponseMessage(200, "회원 가입이 완료 되었습니다.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/api/member/signup/group/join")
    public ResponseEntity<?> groupJoin(@Validated @RequestBody RegisterDTO registerDTO) {
        memberService.join(registerDTO);
        ResponseMessage message = new ResponseMessage(200, "그룹 참여가 완료 되었습니다.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/api/member/rejoin/group")
    public ResponseEntity<?> groupCreateRejoin(@Validated @RequestBody ReJoinDTO reJoinDTD) {
        memberService.createRejoin(reJoinDTD);
        ResponseMessage message = new ResponseMessage(200, "그룹 생성이 완료 되었습니다.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/api/member/rejoin/group/join")
    public ResponseEntity<?> groupReJoin(@Validated @RequestBody ReJoinDTO reJoinDTD ) {
        memberService.rejoin(reJoinDTD);
        ResponseMessage message = new ResponseMessage(200, "그룹 참가가 완료 되었습니다.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/api/member/login")
    public ResponseEntity<ResponseMessage> login(@Validated @RequestBody LoginDTO loginDTO) {
            String accessToken = memberService.login(loginDTO);


        ResponseMessage message = new ResponseMessage(200, "로그인이 완료 되었습니다.", accessToken);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/api/member/logout")
    public ResponseEntity<?> logout(Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        String memberIdKey = String.valueOf(member.getId());
        if (redisTemplate.hasKey(memberIdKey)) {
            redisTemplate.delete(memberIdKey);
        }

        ResponseMessage message = new ResponseMessage(200, "로그아웃 완료");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/api/member")
    public ResponseEntity<ResponseMessage> memberInfo(Authentication authentication) {

        log.info("authentication={}", authentication);
        Member member = (Member) authentication.getPrincipal();

        MemberInfoDTO memberInfo = memberService.getMemberInfo(member.getId());

        ResponseMessage message = new ResponseMessage(200, "회원 정보 조회 완료 되었습니다.", memberInfo);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // 프로젝트 내의 멤버 조회 API
    @GetMapping("/api/project/{projectId}/members")
    public ResponseEntity<?> getMemberOfProject(@PathVariable("projectId") Long projectId) {
        return memberService.getMemberOfProject(projectId);
    }

    // 그룹 내의 멤버 조회 API
    @GetMapping("/api/group/members")
    public ResponseEntity<?> getMemberOfGroup(Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        MemberGroupDTO memberInfoWithGroupMember = memberService.getMemberInfoWithGroupMember(member.getId());
        ResponseMessage message = new ResponseMessage(200, "회원 정보 및 그룹원 조회 완료 되었습니다.", memberInfoWithGroupMember);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PatchMapping("/api/member")
    public ResponseEntity<?> updateMemberInfo(Authentication authentication, @RequestBody MemberUpdateDTO memberUpdateDTO) {
        Member member = (Member) authentication.getPrincipal();

        memberService.updateMember(member.getId(), memberUpdateDTO);

        ResponseMessage message = new ResponseMessage(200,"멤버 정보 수정이 완료 되었습니다.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    //사용자를 그룹에서 삭제
    @DeleteMapping ("/api/groupMember")
    public ResponseEntity<?> deleteMemberGroup(@RequestBody MemberInfoDTO memberInfoDTO) {
        memberService.removeMemberGroup(memberInfoDTO);

        ResponseMessage message = new ResponseMessage(200,memberInfoDTO.getName()+"유저가 그룹에서 삭제됐습니다.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/api/keepAlive")
    public ResponseEntity<ResponseMessage> keepSessionAlive(Authentication authentication) {
        Member member = (Member) authentication.getPrincipal();
        String key = String.valueOf(member.getId());
        String value = "On";
        redisTemplate.opsForValue().set(key, value, 10, TimeUnit.MINUTES);
        ResponseMessage message = new ResponseMessage(200, "세션 유지 요청 성공.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping("/api/member/profileImage")
    public ResponseEntity<ResponseMessage> uploadImage(Authentication authentication, @RequestPart("profileImg") MultipartFile profileImg) {
        memberService.saveImage(authentication, profileImg);

        ResponseMessage message = new ResponseMessage(200, "프로필 이미지를 저장했습니다.");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }



}
