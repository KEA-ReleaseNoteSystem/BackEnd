package kakao99.backend.utils;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class ResponseMessage {
    private HttpStatus statusCode;
    private String message;
    private Object data;


    public ResponseMessage createMessage(HttpStatus statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        return this;
    }

    public ResponseMessage createMessage(HttpStatus statusCode, String message, Object data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
        return this;
    }
}
