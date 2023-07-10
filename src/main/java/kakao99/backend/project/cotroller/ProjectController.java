package kakao99.backend.project.cotroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectController {

    @GetMapping("/api/project")
    public String getProject(){
        return "get요청";
    }
    public String postProject(){
        return null;
    }
    public String putProject(){
        return null;
    }
    public String deleteProject(){
        return null;
    }
}
