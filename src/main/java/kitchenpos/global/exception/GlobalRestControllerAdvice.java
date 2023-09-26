package kitchenpos.global.exception;

import kitchenpos.global.exception.dto.ErrorCode;
import kitchenpos.global.exception.dto.ExceptionResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalRestControllerAdvice {

    @ResponseBody
    @ExceptionHandler(NoSuchElementException.class)
    public ExceptionResponse noSuchElementExceptionHandler(NoSuchElementException exception) {
        return new ExceptionResponse(
                ErrorCode.NO_SUCH_ELEMENT.getCode(),
                ErrorCode.NO_SUCH_ELEMENT.getMessage()
        );
    }

}
