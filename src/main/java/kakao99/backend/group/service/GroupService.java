package kakao99.backend.group.service;


import jakarta.transaction.Transactional;
import kakao99.backend.entity.Group;
import kakao99.backend.group.dto.GroupDTO;
import kakao99.backend.group.dto.GroupNameDTO;
import kakao99.backend.group.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;

    public ResponseEntity<?> createGroup(GroupDTO groupDTO) {
        Group group = groupDTO.toEntity();
        groupRepository.save(group);
        return new ResponseEntity<>("그룹 저장", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> updateGroupName(GroupNameDTO groupNameDTO){
        try {
            Optional<Group> optionalGroup = groupRepository.findByCode(groupNameDTO.getCode());
            Group group = optionalGroup.get();
            group.updateName(groupNameDTO.getName());
        }
        catch(Exception e){
            System.out.println("코드에 맞는 그룹이 존재하지 않습니다.");
        }

        return new ResponseEntity<> ("그룹 이름 수정", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> removeGroup(GroupNameDTO groupNameDTO) {
        try {
            Optional<Group> optionalGroup = groupRepository.findByCode(groupNameDTO.getCode());
            Group group = optionalGroup.get();
            group.deleteGroup();
        }
        catch(Exception e){
            System.out.println("코드에 맞는 그룹이 존재하지 않습니다.");
        }
        return new ResponseEntity<>("그룹 삭제", HttpStatus.OK);
    }

}
