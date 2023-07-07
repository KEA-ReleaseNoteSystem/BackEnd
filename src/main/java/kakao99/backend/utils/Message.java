package kakao99.backend.utils;

import lombok.Data;

@Data
public class Message {
    private Integer statusCode;
    private String message;
    private Object data;

    public Message() {
        this.statusCode = null;
        this.data = null;
        this.message = null;
    }
}
