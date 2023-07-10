package kakao99.backend.project.dto;

import kakao99.backend.entity.Group;
import kakao99.backend.entity.Project;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private String name;    // 제목

    private String description; // 설명

    private String group_id;
    public Project toEntity(Group group){
        return Project.builder()
                .name(this.name)
                .description(this.description)
                .status("running")
                .isActive("true")
                .deletedAt(null)
                .group(group)
                .build();
    }
}
