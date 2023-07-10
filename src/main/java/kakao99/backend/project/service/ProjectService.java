package kakao99.backend.project.service;

import jakarta.transaction.Transactional;
import kakao99.backend.entity.Group;
import kakao99.backend.entity.Project;
import kakao99.backend.group.dto.GroupNameDTO;
import kakao99.backend.group.repository.GroupRepository;
import kakao99.backend.project.dto.ProjectDTO;
import kakao99.backend.project.dto.ProjectModifyDTO;
import kakao99.backend.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final GroupRepository groupRepository;

    public ResponseEntity<?> saveProject(@RequestBody ProjectDTO projectDTO){
        Optional<Group> optionalGroup = groupRepository.findByCode(projectDTO.getGroup_id());
        Group group = optionalGroup.get();
        Project project = projectDTO.toEntity(group);
        projectRepository.save(project);
        return new ResponseEntity<>("프로젝트 저장", HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<?> updateProject(ProjectModifyDTO projectModifyDTO){
        try {
            Optional<Project> optionalProject = projectRepository.findById(projectModifyDTO.getId());
            Project project = optionalProject.get();
            project.updateProject(projectModifyDTO);
        }
        catch(Exception e){
            System.out.println("id에 맞는 프로젝트가 존재하지 않습니다.");
        }

        return new ResponseEntity<> ("프로젝트 수정", HttpStatus.OK);
    }
    @Transactional
    public ResponseEntity<?> removeProject(ProjectModifyDTO projectModifyDTO){
        try {
            Optional<Project> optionalProject = projectRepository.findById(projectModifyDTO.getId());
            Project project = optionalProject.get();
            project.deleteProject();
        }
        catch(Exception e){
            System.out.println("id에 맞는 프로젝트가 존재하지 않습니다.");
        }

        return new ResponseEntity<> ("프로젝트 삭제", HttpStatus.OK);
    }
}
