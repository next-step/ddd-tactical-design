package kitchenpos.global.exception.validation;

public record ValidationError(
    String field,
    String message
) {}
