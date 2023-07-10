package kakao99.backend.project.repository;

import kakao99.backend.entity.Project;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository {
    Project save (Project project);

    Optional<Project> findById(Long id);
    Optional<Project> findByName(String name);
    List<Project> findAll();
    List<Project> findGroupId(Long group);
}
