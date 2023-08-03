package kakao99.backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import kakao99.backend.entity.types.NotificationType;
import kakao99.backend.issue.dto.DragNDropDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;

    private String type;    // 알림 유형

    private String message; // 알림 내용

    private Long typeSpecificId; // 타입에 해당하는 특정id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @JsonManagedReference
    private Project project;

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt; // 생성일

    @Column(name = "is_checked")
    private Boolean isChecked; // 확인 여부


//    public static Notification createdByDragNDrop(DragNDropDTO dragNDropDTO, Member memberReport, Issue issue) {
//
//        return Notification.builder()
//                .memberReport(memberReport)
//                .type(NotificationType.DRAGNDROP.getType())// 이슈 타입
//                .updatedIssueStatusBefore(dragNDropDTO.getSourceStatus())
//                .updatedIssueStatusAfter(dragNDropDTO.getDestinationStatus())
////                .issue(issue)
//                .createdAt(new Date())
//                .updatedAt(null)
//                .deletedAt(null)
//                .isActive(true)  // Assuming memos are active when created
//                .build();
//    }
}
