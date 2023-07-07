package kakao99.backend.release.service;

import kakao99.backend.entity.Member;
import kakao99.backend.entity.Project;
import kakao99.backend.entity.ReleaseNote;
import kakao99.backend.release.DTO.CreateReleaseDTO;
import kakao99.backend.release.repository.ReleaseRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReleaseService {

    private ReleaseRepository releaseRepository;
    public ReleaseNote createRelease(CreateReleaseDTO createReleaseDTO, Member member, Project project) {
        ReleaseNote releaseNote = ReleaseNote.builder()
                .id(createReleaseDTO.getId())
                .version(createReleaseDTO.getVersion())
                .status(createReleaseDTO.getStatus())
                .percent(createReleaseDTO.getPercent())
                .releaseDate(createReleaseDTO.getReleaseDate())
                .brief(createReleaseDTO.getBrief())
                .description(createReleaseDTO.getDescription())
                .createdAt(createReleaseDTO.getCreatedAt())
                .isActive(true)
                .member(member)
                .description(createReleaseDTO.getDescription())
                .project(project)

                .build();

        return releaseRepository.save(releaseNote);
    }

    public List<ReleaseNote> findRelease(Long id) {
        // 해당 프로젝트 id에 맞는 릴리즈 노트 목록 가져오기
        return releaseRepository.findReleaseNoteByProjectId(id);
    }

    public Optional<ReleaseNote> getReleaseInfo(Long id) {
        // 릴리즈 노트 하나 선택하면 해당하는 릴리즈 노트 정보 가져오기
        return releaseRepository.findReleaseNoteById(id);
    }

    public void deleteRelease(Long id, Date deletedAt) {
        // 릴리즈 노트 아이디로 isActive를 False로 바꾸고 지운 시간 넣어주기
        releaseRepository.updateIsActiveById(id, deletedAt);
    }
}
