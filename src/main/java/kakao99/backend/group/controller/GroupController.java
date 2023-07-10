package kakao99.backend.group.controller;

import kakao99.backend.entity.Group;
import kakao99.backend.group.dto.GroupDTO;
import kakao99.backend.group.dto.GroupNameDTO;
import kakao99.backend.group.repository.GroupRepository;
import kakao99.backend.group.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/group")
    public ResponseEntity<?> createGroup(@RequestBody GroupDTO groupDTO) {
        groupDTO.setIsActive("true");
        groupService.createGroup(groupDTO);
        return new ResponseEntity<>("그룹 생성 완료", HttpStatus.OK);
    }

    @PatchMapping("/newGroupName")
    public ResponseEntity<?> updateGroup(@RequestBody GroupNameDTO groupNameDTO) {
        groupService.updateGroupName(groupNameDTO);
        return new ResponseEntity<>("그룹 수정 완료", HttpStatus.OK);
    }

    @DeleteMapping ("/group")
    public ResponseEntity<?> deleteGroup(@RequestBody GroupNameDTO groupNameDTO) {
        groupService.removeGroup(groupNameDTO);
        return new ResponseEntity<>("그룹 삭제 완료", HttpStatus.OK);
    }
}
