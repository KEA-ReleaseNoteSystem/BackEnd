package kakao99.backend.project.repository;

import kakao99.backend.entity.Issue;
import kakao99.backend.entity.Project;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Override
    Optional<Project> findById(Long projectId);

//    Project findById(Long ProjectId);

//    @Query("select m from Project m join fetch m.group")
//    List<Project> findAllMemberInGroup();


}
