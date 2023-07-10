package kakao99.backend.group.dto;

import kakao99.backend.entity.Group;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {
    private String name;
    private String isActive;
    public Group toEntity() {
        return Group.builder()
                .name(this.name)
                .isActive(this.isActive)
                .build();
    }


}