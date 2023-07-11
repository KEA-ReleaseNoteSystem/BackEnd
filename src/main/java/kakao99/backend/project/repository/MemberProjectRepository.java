package kakao99.backend.project.repository;

import kakao99.backend.entity.MemberProject;
import kakao99.backend.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberProjectRepository extends JpaRepository<MemberProject, Long> {
    MemberProject save (MemberProject memberProject);

    Optional<MemberProject> findAllByProjectIdAndMemberId(Long project_id, Long member_id);
}
