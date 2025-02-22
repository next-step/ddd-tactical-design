package kitchenpos.global.exception.validation;

import java.util.List;

public record ValidationErrorResponse(
    String code,
    String message,
    List<ValidationError> errors
) {}

