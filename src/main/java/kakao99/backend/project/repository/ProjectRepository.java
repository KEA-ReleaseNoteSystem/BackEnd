package kakao99.backend.project.repository;

import kakao99.backend.entity.Project;
import kakao99.backend.entity.ReleaseNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project save (Project project);

    Optional<Project> findById(Long id);
    Optional<Project> findByName(String name);
    List<Project> findAll();
    //List<Project> findByGroupId(Long group);
}
