package kakao99.backend.issue.DTO;


import kakao99.backend.entity.Member;
import kakao99.backend.entity.Memo;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class IssueDTO {

    private Long id;
    private Integer issueNum;
    private String title;
    private String issueType;
    private String description;
    private String status;
    private Integer listPosition;
    private String file;
    private Date createdAt;
    private Long memberIdInCharge;
    private Long memberIdReport;
//    private List<Memo> comments;

    public IssueDTO() {

    }
}


