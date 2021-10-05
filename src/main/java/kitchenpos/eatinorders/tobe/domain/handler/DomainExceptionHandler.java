package kitchenpos.eatinorders.tobe.domain.handler;

import kitchenpos.eatinorders.tobe.domain.model.ExceptionType;

public interface DomainExceptionHandler {
    void handle(ExceptionType type);
}
