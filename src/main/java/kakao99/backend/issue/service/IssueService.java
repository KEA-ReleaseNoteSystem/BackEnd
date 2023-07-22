package kakao99.backend.issue.service;

import kakao99.backend.common.exception.CustomException;
import kakao99.backend.entity.Issue;
import kakao99.backend.entity.Member;
import kakao99.backend.issue.controller.UpdateIssueForm;
import kakao99.backend.issue.dto.DragNDropDTO;
import kakao99.backend.issue.dto.IssueDTO;
import kakao99.backend.issue.dto.MemberInfoDTO;
import kakao99.backend.issue.dto.ProjectWithIssuesDTO;
import kakao99.backend.issue.repository.IssueRepository;
import kakao99.backend.issue.repository.IssueRepositoryImpl;
import kakao99.backend.member.repository.MemberRepository;
import kakao99.backend.project.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class IssueService {
    private final IssueRepository issueRepository;
    private final MemberRepository memberRepository;
    private final ProjectService projectService;

    public List<Issue> getIssuesWithMemo(Long projectId) {
        return issueRepository.findAllByProjectId(projectId);
    }


    public List<IssueDTO> getAllIssues(Long projectId) {
        List<Issue> allIssueByProjectId = issueRepository.findAllByProjectId(projectId);
        System.out.println("allIssueByProjectId.toArray().length = " + allIssueByProjectId.toArray().length);
        List<IssueDTO> issueDTOListFromIssueList = IssueDTO.getIssueDTOListFromIssueList(allIssueByProjectId);

        return issueDTOListFromIssueList;
    }


    public List<IssueDTO> getAllIssuesByFilter(Long projectId ,String status, String type,String name) {

        List<Issue> allIssueByProjectId = issueRepository.findAllWithFilter(projectId, status, type, name);

        List<IssueDTO> issueDTOListFromIssueList = IssueDTO.getIssueDTOListFromIssueList(allIssueByProjectId);

        return issueDTOListFromIssueList;
    }


public void updateIssue(UpdateIssueForm updateIssueForm, Long issueId) {
//    issueRepositoryImpl.updateIssue(updateIssueForm, issueId);
    issueRepository.updateIssue(updateIssueForm, issueId);
    }

    public List<IssueDTO> getIssueListIncludedInReleaseNote(Long releaseNoteId) {
        List<Issue> issueListIncludedInReleaseNote = issueRepository.findAllByReleaseNoteId(releaseNoteId);

        if (issueListIncludedInReleaseNote == null || issueListIncludedInReleaseNote.isEmpty()) {
            throw new NoSuchElementException("릴리즈 노트에 포함된 이슈가 없습니다.");
        }
        List<IssueDTO> issueDTOListFromIssueList = IssueDTO.getIssueDTOListFromIssueList(issueListIncludedInReleaseNote);
        return issueDTOListFromIssueList;
    }


    public List<IssueDTO> getIssueListNotIncludedInReleaseNote(Long projectId) {
        List<Issue> issueListNotIncludedInReleaseNote = issueRepository.findAllByNotReleaseNoteId(projectId);

        if (issueListNotIncludedInReleaseNote == null || issueListNotIncludedInReleaseNote.isEmpty()) {
            throw new NoSuchElementException("릴리즈 노트에 포함되지 않은 이슈가 없습니다.");
        }

        List<IssueDTO> issueDTOListFromIssueList = IssueDTO.getIssueDTOListFromIssueList(issueListNotIncludedInReleaseNote);
       return issueDTOListFromIssueList;
    }

    // issue management 페이지에서 필요한 데이터 get
    public ProjectWithIssuesDTO getIssueManagementPageData(Long projectId) {
        // 프로젝트 info
        ProjectWithIssuesDTO projectInfo = projectService.getProjectIdAndName(projectId);

        // issue List
        List<IssueDTO> allIssues = getAllIssues(projectId);
        projectInfo.saveIssueList(allIssues);

        // memo

        return projectInfo;
    }

    @Transactional
    public Long deleteIssue(Long issueId, Long memberId) {
        Optional<Issue> issueByIssueId = issueRepository.findIssueById(issueId);
        if (issueByIssueId.isEmpty()) {
            throw new CustomException(404, issueByIssueId + "번 이슈가 존재하지 않습니다.");
        }

        issueRepository.deleteIssue(issueId, memberId);

        return issueId;
    }


    public void updateIssueByDragNDrop(DragNDropDTO dragNDropDTO) {
        issueRepository.updateIssueByDragNDrop(dragNDropDTO);
    }

}
