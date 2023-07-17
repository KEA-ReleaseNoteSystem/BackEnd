package kakao99.backend.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerController extends ResponseEntityExceptionHandler{

        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<?> testException(CustomException e) {

            CustomErrorMessage customErrorMessage = new CustomErrorMessage(e.getStatusCode(), e.getMessage(), e.getPlace());

            return new ResponseEntity<>(customErrorMessage, HttpStatus.NOT_IMPLEMENTED);
        }

}
