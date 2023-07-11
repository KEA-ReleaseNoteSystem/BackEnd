package kakao99.backend.issue.dto;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.util.Date;

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

////    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
//    @ManyToOne(fetch = FetchType.LAZY)
    private MemberInfoDTO memberIdInCharge;
//    private Long memberIdReport;
//    private List<Memo> comments;

}


