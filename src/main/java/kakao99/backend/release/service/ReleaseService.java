package kakao99.backend.release.service;

import kakao99.backend.entity.*;
import kakao99.backend.entity.types.NotificationType;
import kakao99.backend.issue.repository.IssueRepository;
import kakao99.backend.member.repository.MemberRepository;
import kakao99.backend.notification.rabbitmq.dto.RequestMessageDTO;
import kakao99.backend.notification.service.NotificationService;
import kakao99.backend.release.dto.CreateReleaseDTO;
import kakao99.backend.release.dto.UpdateReleaseDTO;
import kakao99.backend.release.repository.ReleaseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReleaseService {

    private final ReleaseRepository releaseRepository;
    private final MemberRepository memberRepository;
    private final IssueRepository issueRepository;
    private final NotificationService notificationService;

    @Transactional
    public ReleaseNote createRelease(CreateReleaseDTO createReleaseDTO, Member member, Project project) {

        ReleaseNote newReleaseNote = ReleaseNote.builder()
                .version(createReleaseDTO.getVersion())
                .status(createReleaseDTO.getStatus())
                .percent(createReleaseDTO.getPercent())
                .releaseDate(createReleaseDTO.getReleaseDate())
                .brief(createReleaseDTO.getBrief())
                .description(createReleaseDTO.getDescription())
                .isActive(true)
                .member(member)
                .project(project)
                .deletedAt(null)
                .build();

        releaseRepository.save(newReleaseNote);

        RequestMessageDTO requestMessageDTO = new RequestMessageDTO().builder()
                .type(NotificationType.RELEASENOTECREATED)
                .specificTypeId(newReleaseNote.getId())
                .projectId(newReleaseNote.getProject().getId())
                .build();

        notificationService.createNotification(requestMessageDTO);

        return newReleaseNote;
    }

    public List<ReleaseNote> findRelease(Long id) {
        // 해당 프로젝트 id에 맞는 릴리즈 노트 목록 가져오기
        return releaseRepository.findByProjectIdAndIsActiveTrue(id);
    }

    public Optional<ReleaseNote> getReleaseInfo(Long id) {
        // 릴리즈 노트 하나 선택하면 해당하는 릴리즈 노트 정보 가져오기
        return releaseRepository.findById(id);
    }


//            releaseService.updateRelease(updateReleaseDTO.getReleaseId(), updateReleaseDTO.getVersion(), updateReleaseDTO.getStatus(),
//                    updateReleaseDTO.getPercent(), updateReleaseDTO.getReleaseDate(), updateReleaseDTO.getBrief(), updateReleaseDTO.getDescription());

    @Transactional
    public void updateRelease(UpdateReleaseDTO updateReleaseDTO) {

        releaseRepository.updateReleaseNoteById(updateReleaseDTO.getReleaseId(), updateReleaseDTO.getVersion(),
                updateReleaseDTO.getStatus(), updateReleaseDTO.getPercent(), updateReleaseDTO.getReleaseDate(),
                updateReleaseDTO.getBrief(), updateReleaseDTO.getDescription());

        ReleaseNote releaseNote = releaseRepository.findById(updateReleaseDTO.getReleaseId()).get();



        RequestMessageDTO requestMessageDTO = new RequestMessageDTO().builder()
                .type(NotificationType.RELEASENOTECHANGED)
                .specificTypeId(updateReleaseDTO.getReleaseId())
                .projectId(releaseNote.getProject().getId())
                .build();

        notificationService.createNotification(requestMessageDTO);
    }

    @Transactional
    public void updateIssues(Long releaseId, List<Issue> newIssueList) {   // issueList : 결과물
        List<Issue> oldIssueListOfReleaseNote = issueRepository.findAllByReleaseNoteId(releaseId);

        // 추가
        for (Issue issue : newIssueList) {
            Long issueId = issue.getId();
            boolean foundInOldList = false;

            for (Issue oldIssue : oldIssueListOfReleaseNote) {
                if (issueId.equals(oldIssue.getId())) {
                    foundInOldList = true;
                    break;
                }
            }

            if (!foundInOldList) {
                Optional<Issue> existingIssue = issueRepository.findById(issueId); // Assuming you have an issueRepository to retrieve the existing issue by its ID
                int res = issueRepository.insertIssueFromReleaseNote(releaseId, existingIssue.get().getId()); // Assuming you have a method addReleaseNote in your Issue class to associate the releaseNote
            }
        }

        // 삭제
        for (Issue issue : oldIssueListOfReleaseNote) {
            Long issueId = issue.getId();
            boolean foundInNewList = false;

            for (Issue newIssue : newIssueList) {
                if (issueId.equals(newIssue.getId())) {
                    foundInNewList = true;
                    break;
                }
            }

            if (!foundInNewList) {
                Optional<Issue> deleteIssue = issueRepository.findById(issueId); // Assuming you have an issueRepository to retrieve the existing issue by its ID
                int res = issueRepository.deleteIssueFromReleaseNote(deleteIssue.get().getId()); // Assuming you have a method addReleaseNote in your Issue class to associate the releaseNote
            }
        }

        return;
    }

    @Transactional
    public void deleteRelease(Long releaseNoteId) {
        // 릴리즈 노트 아이디로 isActive를 False로 바꾸고 지운 시간 넣어주기
        releaseRepository.updateIsActiveById(releaseNoteId, new Date());

        ReleaseNote releaseNote = releaseRepository.findById(releaseNoteId).get();

        RequestMessageDTO requestMessageDTO = new RequestMessageDTO().builder()
                .type(NotificationType.RELEASENOTEDELETED)
                .specificTypeId(releaseNoteId)
                .projectId(releaseNote.getProject().getId())
                .build();

        notificationService.createNotification(requestMessageDTO);
    }
}
