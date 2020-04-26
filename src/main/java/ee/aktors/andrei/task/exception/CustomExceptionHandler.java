package ee.aktors.andrei.task.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionDto> handleCustomException(CustomException ex) {
        ExceptionDto exceptionDto = new ExceptionDto();
        exceptionDto.setMessage(ex.getMessage());
        exceptionDto.setStatus(ex.getHttpStatus().value());
        exceptionDto.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(exceptionDto, ex.getHttpStatus());
    }

}
