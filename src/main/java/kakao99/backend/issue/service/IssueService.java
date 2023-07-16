package kakao99.backend.issue.service;

import kakao99.backend.entity.Issue;
import kakao99.backend.entity.Member;
import kakao99.backend.issue.dto.IssueDTO;
import kakao99.backend.issue.dto.MemberInfoDTO;
import kakao99.backend.issue.repository.IssueRepository;
import kakao99.backend.issue.repository.IssueStatus;
import kakao99.backend.issue.repository.IssueType;
import kakao99.backend.member.repository.MemberRepository;
import kakao99.backend.project.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IssueService {
    private final IssueRepository issueRepository;
    private final MemberRepository memberRepository;

    public List<Issue> getIssuesWithMemo(Long projectId) {
        return issueRepository.findAllByProjectId(projectId);
    }


    public ArrayList<IssueDTO> getAllIssues(Long projectId) {
        List<Issue> allIssueByProjectId = issueRepository.findAllByProjectId(projectId);
        ArrayList<IssueDTO> issueDTOList = new ArrayList<>();

        for (Issue issue : allIssueByProjectId) {

            Member memberInCharge = issue.getMemberInCharge();
            Member memberReport = issue.getMemberReport();

            MemberInfoDTO memberInChargeInfoDTO = MemberInfoDTO.builder()
                    .name(memberInCharge.getUsername())
                    .nickname(memberInCharge.getNickname())
                    .email(memberInCharge.getEmail())
                    .position(memberInCharge.getPosition())
                    .build();

            MemberInfoDTO memberReportInfoDTO = MemberInfoDTO.builder()
                    .name(memberReport.getUsername())
                    .nickname(memberReport.getNickname())
                    .email(memberReport.getEmail())
                    .position(memberReport.getPosition())
                    .build();

            IssueDTO issueDTO = IssueDTO.builder()
                    .id(issue.getId())
                    .issueNum(issue.getIssueNum())
                    .title(issue.getTitle())
                    .issueType(issue.getIssueType())
                    .description(issue.getDescription())
                    .status(issue.getStatus())
                    .file(issue.getFile())
                    .createdAt(issue.getCreatedAt())
                    .memberIdInCharge(memberInChargeInfoDTO)
                    .memberReport(memberReportInfoDTO)
                    .importance(issue.getImportance())


                    .build();

            issueDTOList.add(issueDTO);
        }
    return issueDTOList;
    }

    public ArrayList<IssueDTO> getAllIssuesByFilter(Long projectId ,String state) {

        List<Issue> allIssueByProjectId = null;

        if (state != null && EnumUtils.isValidEnumIgnoreCase(IssueStatus.class, state.toUpperCase(Locale.ROOT))) {
            allIssueByProjectId = issueRepository.findAllByStatus(projectId, state);
        }else if(state != null && EnumUtils.isValidEnumIgnoreCase(IssueType.class, state.toUpperCase(Locale.ROOT))){
            allIssueByProjectId = issueRepository.findAllByType(projectId, state);
        }



        ArrayList<IssueDTO> issueDTOList = new ArrayList<>();

        for (Issue issue : allIssueByProjectId) {

            Member memberInCharge = issue.getMemberInCharge();
            MemberInfoDTO memberInfoDTO = MemberInfoDTO.builder()
                    .name(memberInCharge.getUsername())
                    .nickname(memberInCharge.getNickname())
                    .email(memberInCharge.getEmail())
                    .position(memberInCharge.getPosition())
                    .build();

            IssueDTO issueDTO = IssueDTO.builder()
                    .id(issue.getId())
                    .issueNum(issue.getIssueNum())
                    .title(issue.getTitle())
                    .issueType(issue.getIssueType())
                    .description(issue.getDescription())
                    .status(issue.getStatus())
                    .file(issue.getFile())
                    .createdAt(issue.getCreatedAt())
                    .memberIdInCharge(memberInfoDTO)
                    .releasenote(issue.getReleaseNote().getVersion())
                    .build();

            issueDTOList.add(issueDTO);
        }
        return issueDTOList;
    }



    public String updateIssue(String title, String description,String status, String issueType,Long issueId) {

        System.out.println("title = " + title);
        if (title==null && description != null) {
            issueRepository.updateIssueDescription(description, issueId);
        }
        else if (title != null && description==null) {
            issueRepository.updateIssueTitle(title, issueId);
        }else if (title!= null && description != null){
            issueRepository.updateIssue(title, description, issueId);
        }else if (title == null && description == null && issueType == null){
            issueRepository.updateIssueStatus(status, issueId);
        }else if (title == null && description == null && status == null){
            issueRepository.updateIssueType(issueType, issueId);
        }
        else{
            return "파라미터 전달되지 않음.";
        }
        return "OK";
    }


    public ArrayList<IssueDTO> getAllIssuesByReleaseNoteId(Long releaseNoteId) {
        List<Issue> allIssuesByReleaseNoteId = issueRepository.findAllByReleaseNoteId(releaseNoteId);
        ArrayList<IssueDTO> issueDTOList = new ArrayList<>();
        for (Issue issue : allIssuesByReleaseNoteId) {

            Member memberInCharge = issue.getMemberInCharge();
            Member memberReport = issue.getMemberReport();

            MemberInfoDTO memberInChargeInfoDTO = MemberInfoDTO.builder()
                    .name(memberInCharge.getUsername())
                    .nickname(memberInCharge.getNickname())
                    .email(memberInCharge.getEmail())
                    .position(memberInCharge.getPosition())
                    .build();

            MemberInfoDTO memberReportInfoDTO = MemberInfoDTO.builder()
                    .name(memberReport.getUsername())
                    .nickname(memberReport.getNickname())
                    .email(memberReport.getEmail())
                    .position(memberReport.getPosition())
                    .build();

            IssueDTO issueDTO = IssueDTO.builder()
                    .id(issue.getId())
                    .issueNum(issue.getIssueNum())
                    .title(issue.getTitle())
                    .issueType(issue.getIssueType())
                    .description(issue.getDescription())
                    .status(issue.getStatus())
                    .file(issue.getFile())
                    .createdAt(issue.getCreatedAt())
                    .memberIdInCharge(memberInChargeInfoDTO)
                    .memberReport(memberReportInfoDTO)
                    .importance(issue.getImportance())

                    .build();

            issueDTOList.add(issueDTO);
        }
        return issueDTOList;
    }

    public ArrayList<IssueDTO> findAllByNotReleaseNoteId(Long projectId){
        List<Issue> allByNotReleaseNoteId = issueRepository.findAllByNotReleaseNoteId(projectId);
        System.out.println("allByNotReleaseNoteId = " + allByNotReleaseNoteId);
        ArrayList<IssueDTO> issueDTOList = new ArrayList<>();

        for (Issue issue : allByNotReleaseNoteId) {

            Member memberInCharge = issue.getMemberInCharge();
            Member memberReport = issue.getMemberReport();

            MemberInfoDTO memberInChargeInfoDTO = MemberInfoDTO.builder()
                    .name(memberInCharge.getUsername())
                    .nickname(memberInCharge.getNickname())
                    .email(memberInCharge.getEmail())
                    .position(memberInCharge.getPosition())
                    .build();

            MemberInfoDTO memberReportInfoDTO = MemberInfoDTO.builder()
                    .name(memberReport.getUsername())
                    .nickname(memberReport.getNickname())
                    .email(memberReport.getEmail())
                    .position(memberReport.getPosition())
                    .build();

            IssueDTO issueDTO = IssueDTO.builder()
                    .id(issue.getId())
                    .issueNum(issue.getIssueNum())
                    .title(issue.getTitle())
                    .issueType(issue.getIssueType())
                    .description(issue.getDescription())
                    .status(issue.getStatus())
                    .file(issue.getFile())
                    .createdAt(issue.getCreatedAt())
                    .memberIdInCharge(memberInChargeInfoDTO)
                    .memberReport(memberReportInfoDTO)
                    .importance(issue.getImportance())

                    .build();

            issueDTOList.add(issueDTO);
        }
        return issueDTOList;
    }
}
