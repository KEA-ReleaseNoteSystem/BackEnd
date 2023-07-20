package kakao99.backend.memo.repository;


import kakao99.backend.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Long> {


    List<Memo> findAllByIssueId(Long issueId);
    Optional<Memo> findById(Long id);


    List<Memo> findByIssueIdAndIsActiveTrue(Long issueId);
//
//    @Transactional
//    @Modifying
//    @Query("UPDATE Memo m SET m.isActive = false, m.deletedAt = :deletedAt WHERE m.id = :id")
//    void updateIsActiveById(@Param("id") Long id, @Param("deletedAt") Date deletedAt);
}
