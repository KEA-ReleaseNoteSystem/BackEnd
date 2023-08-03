package kakao99.backend.entity.types;

import lombok.Getter;

@Getter
public enum NotificationType {
    ISSUECREATED("issue"),
    ISSUEDONE("issue"),
    ISSUEDELETED("issue"),
    ISSUEALLOCATED("issue"),
    ISSUECHANGED("issue"),

    RELEASENOTECREATED("releaseNote"),
    RELEASENOTECHANGED("releaseNote"),
    RELEASENOTEDELETED("releaseNote"),

    NEWMEMBER("member"),
    OUTMEMBER("member");

    private String type;

    NotificationType(String type) {
        this.type = type;
    }

//    ISSUEMADE("dragNDrop", "로 상태 변경되었습니다."),
//    ALLOCATED("allocated", "이슈를 할당했습니다.");
//

//    private String subjectMessage;
//    private String verbMessage;
//
//    NotificationType(String type, String subjectMessage, String verbMessage) {
//        this.type = type;
//        this.subjectMessage = subjectMessage;
//        this.verbMessage = verbMessage;
//    }

}
