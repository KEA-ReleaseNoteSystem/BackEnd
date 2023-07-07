package kakao99.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "Release_notes")
public class ReleaseNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "release_note_id")
    private Long id;

    private String version; // 버전

    private String status;  // 상태

    private Float percent;  // 진행률

    @Column(name = "release_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date releaseDate;   // 배포일

    private String brief;   //  요약

    private String description; //세부 설명

    @Column(name = "created_at", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt; // 생성일

    @Column(name = "updated_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updatedAt; // 수정일

    @Column(name = "deleted_at", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date deletedAt; // 삭제일

    @Column(name = "is_active")
    private Boolean isActive; // false: 탈퇴한 회원, true: 탈퇴x 회원도

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member member;
}