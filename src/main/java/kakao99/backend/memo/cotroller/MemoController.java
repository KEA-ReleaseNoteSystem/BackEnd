package kakao99.backend.memo.cotroller;

import kakao99.backend.memo.dto.CreateMemoDTO;
import kakao99.backend.memo.dto.UpdateMemoDTO;
import kakao99.backend.memo.dto.memoDTO;
import kakao99.backend.memo.repository.MemoRepository;
import kakao99.backend.memo.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    private final MemoRepository memoRepository;



    @GetMapping("/api/memo/{projectId}/{issueId}")
    public ResponseEntity<?> getMemo(@PathVariable Long issueId)
    {
        List<memoDTO> memo = memoService.getAllMemo(issueId);
        System.out.println("memo" + memo);
        return new ResponseEntity<>("댓글 조회" , HttpStatus.OK);
    }

    @PostMapping("/api/memo/{projectId}/{issueId}/new")
    public ResponseEntity<?> createMemo(@PathVariable Long issueId ,@RequestBody CreateMemoDTO obj)
    {
        memoService.createMemo(obj.getMemberId(),obj.getIssueId(),obj.getContent(),obj.getCreatedAt());
        return new ResponseEntity<>("댓글 생성" , HttpStatus.OK);

    }

    @PatchMapping("/api/memo/{projectId}/{issueId}/{memoId}/patch")
    public ResponseEntity<?> patchMemo(@PathVariable Long memoId,@RequestBody UpdateMemoDTO obj)
    {
        ResponseEntity<?> updatedMemoId = memoService.updateMemo(memoId,obj);
        return new ResponseEntity<>("댓글 수정" , HttpStatus.OK);

    }


    @DeleteMapping("/api/memo/{projectId}/{issueId}/{memoId}/delete")
    public ResponseEntity<?> deleteMemo(@PathVariable Long memoId)
    {
        ResponseEntity<?> updatedMemoId = memoService.deleteMemo(memoId);

        return new ResponseEntity<>("댓글 삭제" , HttpStatus.OK);

    }


}
