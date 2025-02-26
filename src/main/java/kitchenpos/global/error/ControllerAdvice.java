package kitchenpos.global.error;

import jakarta.servlet.http.HttpServletRequest;
import kitchenpos.global.ApiResultResponse;
import kitchenpos.global.error.dto.ErrorReportRequest;
import kitchenpos.global.error.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
public class ControllerAdvice {

    public static final String ERROR = "error";

    private static final Logger log = LoggerFactory.getLogger(ControllerAdvice.class);

    private static final String INVALID_DTO_FIELD_ERROR_MESSAGE_FORMAT = "The %s field is %s (provided value: %s)"; // %s 필드는 %s (전달된 값: %s)

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiResultResponse<ErrorResponse>> handleDomainException(final DomainException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getMessage());
        ApiResultResponse<ErrorResponse> apiResultResponse = ApiResultResponse.failure(errorResponse, ERROR);
        return new ResponseEntity<>(apiResultResponse, e.getHttpStatus());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResultResponse<ErrorResponse>> handleBusinessException(final BusinessException e) {
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode(), e.getMessage());
        ApiResultResponse<ErrorResponse> apiResultResponse = ApiResultResponse.failure(errorResponse, ERROR);
        return new ResponseEntity<>(apiResultResponse, e.getHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResultResponse<ErrorResponse>> handleMethodArgumentException(final MethodArgumentNotValidException e) {
        FieldError firstFieldError = e.getFieldErrors().get(0);
        String errorMessage = String.format(INVALID_DTO_FIELD_ERROR_MESSAGE_FORMAT,
                firstFieldError.getField(), firstFieldError.getDefaultMessage(), firstFieldError.getRejectedValue());

        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INVALID_DTO_FIELD, errorMessage);
        ApiResultResponse<ErrorResponse> apiResultResponse = ApiResultResponse.failure(errorResponse, ERROR);
        return new ResponseEntity<>(apiResultResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler (HttpMessageNotReadableException.class) // 잘못된 요청 본문 형식에 대한 에러 메시지를 클라이언트에 반환
    public ResponseEntity<ApiResultResponse<ErrorResponse>> handleInvalidRequestBodyException() {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INVALID_REQUEST_BODY); // 잘못된 형식의 RequestBody 입니다.
        ApiResultResponse<ErrorResponse> apiResultResponse = ApiResultResponse.failure(errorResponse, ERROR);
        return new ResponseEntity<>(apiResultResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler (MissingServletRequestParameterException.class) // @RequestParam의 누락된 파라미터를 처리
    public ResponseEntity<ApiResultResponse<ErrorResponse>> handleInvalidRequestParamException() {
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INVALID_REQUEST_PARAM); // 잘못된 형식의 RequestBody 입니다.
        ApiResultResponse<ErrorResponse> apiResultResponse = ApiResultResponse.failure(errorResponse, ERROR);
        return new ResponseEntity<>(apiResultResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            Exception.class,
            IOException.class,
    })
    public ResponseEntity<ApiResultResponse<ErrorResponse>> handlerUnexpectedException(final Exception e, final HttpServletRequest request) {
        ErrorReportRequest errorReportRequest = new ErrorReportRequest(request, e);
        log.error(errorReportRequest.getLogMessage(), e);
        ErrorResponse errorResponse = new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
        ApiResultResponse<ErrorResponse> apiResultResponse = ApiResultResponse.failure(errorResponse, ERROR);
        return new ResponseEntity<>(apiResultResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
