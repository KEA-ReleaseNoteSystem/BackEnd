package kakao99.backend.release.DTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReleaseDTO {
    private Long id;
    private String version; // 버전
    private String status;  // 상태
    private Float percent;  // 진행률
    private Date releaseDate;   // 배포일
    private String brief;   //  요약
    private String description; //세부 설명
    private Date createdAt; // 생성일
    private Boolean isActive; // False이면 삭제된 Release note: 읽을땐 True인 것만 목록에 표시
}
