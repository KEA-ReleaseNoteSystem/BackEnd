package kakao99.backend.release.repository;

import kakao99.backend.entity.Project;
import kakao99.backend.entity.ReleaseNote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReleaseRepository extends JpaRepository<ReleaseNote, Long>, ReleaseRepositoryCustom{

    ReleaseNote save(ReleaseNote releaseNote);
    // 생성, 수정

    boolean existsByVersion(String version);



    boolean existsByVersionLike(String versionPattern);



    ReleaseNote findByVersionAndProject(String parentVersion, Project project);
    @Query("SELECT rn FROM ReleaseNote rn WHERE rn.project.id = :projectId AND rn.id NOT IN (SELECT rpc.childNote.id FROM ReleaseNoteParentChild rpc)")
    List<ReleaseNote> findRootNodesByProjectId(@Param("projectId") Long projectId);


    List<ReleaseNote> findByProjectIdAndIsActiveTrue(Long id);
    // 각 프로젝트 대시보드에서 해당 프로젝트에 해당하는 릴리즈 노트 목록 가져오기

    Optional<ReleaseNote> findById(Long id);
    // 릴리즈 노트 목록에서 선택하면 내용 가져오기

    @Transactional
    @Modifying
    @Query("UPDATE ReleaseNote e SET e.version = :version, e.status = :status, e.percent = :percent, e.releaseDate = :releaseDate, e.brief = :brief, " +
            "e.description = :description, e.updatedAt = current date WHERE e.id = :id")
    void updateReleaseNoteById(@Param("id") Long id, @Param("version") String version, @Param("status") String status, @Param("percent")Float percent,
                               @Param("releaseDate")Date releaseDate, @Param("brief") String brief, @Param("description") String description);

    @Transactional
    @Modifying
    @Query("UPDATE ReleaseNote e SET e.isActive = false, e.deletedAt = :deletedAt WHERE e.id = :id")
    void updateIsActiveById(@Param("id") Long id, @Param("deletedAt") Date deletedAt);


    // 릴리즈노트 삭제: isActive 상태를 변경하고 삭제한 시간을 입력

}
