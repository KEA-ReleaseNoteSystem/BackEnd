package kakao99.backend.release.repository;

import kakao99.backend.entity.ReleaseNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReleaseRepository extends JpaRepository<ReleaseNote, Long> {
    ReleaseNote save(ReleaseNote releaseNote);

    //Optional<ReleaseNote> findReleaseNoteByProject(Long id); 프로젝트 아이디로 탐색하고 싶은데 어떻게?
}
