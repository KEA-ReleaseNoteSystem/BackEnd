package kakao99.backend.common.exception;

public enum ErrorCode {

    // Success
    SUCCESS(true, 2000, "요청에 성공하였습니다."),

    // Common
    // 입력값이 없거나 잘못된 값을 입력했을 때.
    INVALID_INPUT_VALUE(false, 4000, "입력값을 확인해주세요."),

    // 토큰이 없거나 유효하지 않은 상태에서 정보를 요청할 때.
    INVALID_JWT(false, 4001, "토큰이 없거나, 유효하지 않습니다. 로그인을 해주세요."),

    // 특정 정보를 권한이 없는 유저가 요청하거나, 존재하지 않는 정보를 요청할 때.
    INVALID_REQUEST(false, 4002, "잘못된 요청입니다."),

    //아이디와 비밀번호가 일치하지 않는 경우
    ACCESS_DENIED_LOGIN(false, 4003, "아이디와 비밀번호가 일치하지 않습니다."),

    //중복된 이메일이 존재할 경우
    DUPLICATE_EMAIL(false, 4004, "이메일로 가입된 아이디가 존재합니다."),

    //그룹 코드가 존재하지 않는 경우
    NOT_MATCH_CODE(false, 4005, "코드가 일치하지 않습니다."),

    //이메일이 존재하지 않는 경우
    NOT_FOUND_EMAIL(false, 4006, "이메일로 가입된 아이디가 존재하지 않습니다."),

    // 파일 관련 처리 실패
    FAIL_PROCESS_FILE(false, 5000, "파일 처리에 실패하였습니다.");

    private Boolean isSuccess;
    private int code;
    private String message;

    ErrorCode(Boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
