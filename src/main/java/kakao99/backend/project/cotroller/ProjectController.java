package kakao99.backend.project.cotroller;

import kakao99.backend.entity.Member;
import kakao99.backend.entity.Project;
import kakao99.backend.project.dto.ProjectDTO;
import kakao99.backend.project.dto.ProjectModifyDTO;
import kakao99.backend.project.repository.ProjectRepository;
import kakao99.backend.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectRepository projectRepository;
    @PostMapping("/project")
    public ResponseEntity<?> createProject(@RequestBody ProjectDTO projectDTO){
        projectService.saveProject(projectDTO);

        return new ResponseEntity<>("프로젝트 생성 완료", HttpStatus.OK);
    }

    @PatchMapping("/project")
    public ResponseEntity<?> patchProject(@RequestBody ProjectModifyDTO projectModifyDTO){
        projectService.updateProject(projectModifyDTO);
        return new ResponseEntity<>("프로젝트 수정 완료", HttpStatus.OK);
    }

    @DeleteMapping("/project")
    public ResponseEntity<?> removeProject(@RequestBody ProjectModifyDTO projectModifyDTO){
        projectService.removeProject(projectModifyDTO);
        return new ResponseEntity<>("프로젝트 삭제 완료", HttpStatus.OK);
    }

    @GetMapping("/project")
    public List<?> getProject(Authentication authentication){
        Member member = (Member) authentication.getPrincipal();
        System.out.println(member.getGroup().getCode());
        List<Project> project = projectRepository.findAllByGroupCode(member.getGroup().getCode());
        return project;
    }

}
