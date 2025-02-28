package kitchenpos.global.infrastructure;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import kitchenpos.global.exception.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@RestControllerAdvice
public class ApiControllerAdvice extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ApiControllerAdvice.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        log.error("Unhandled exception: {}", ex.getMessage(), ex);
        return ResponseEntity.internalServerError()
            .body(ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException ex, @NonNull HttpHeaders headers,
        @NonNull HttpStatusCode status,
        @NonNull WebRequest request) {

        List<ValidationError> validationErrors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(error -> new ValidationError(
                error.getField(),
                error.getDefaultMessage()
            ))
            .toList();

        log.error("Validation Error: {}", ex.getBindingResult().getAllErrors());

        return ResponseEntity.badRequest()
            .body(validationErrors);
    }

    @Override
protected ResponseEntity<Object> handleHttpMessageNotReadable(
        HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

    ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "잘못된 JSON 요청입니다.");
    problemDetail.setInstance(URI.create(request.getDescription(false)));

    List<ValidationError> errors = new ArrayList<>();

    Throwable cause = ex.getCause();
    if (cause instanceof JsonMappingException jsonMappingException) {
        for (JsonMappingException.Reference reference : jsonMappingException.getPath()) {
            ValidationError errorDetail = new ValidationError(
                    reference.getFieldName(),
                    "Invalid value or missing field"
            );
            errors.add(errorDetail);
        }
    } else if (cause instanceof JsonParseException jsonParseException) {
        ValidationError errorDetail = new ValidationError(
                null, jsonParseException.getOriginalMessage()
        );
        errors.add(errorDetail);
    }

    if (!errors.isEmpty()) {
        problemDetail.setProperty("errors", errors);
    }

    return new ResponseEntity<>(problemDetail, headers, HttpStatus.BAD_REQUEST);
}


}
