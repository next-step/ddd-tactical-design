package kitchenpos.eatinorders.tobe.infra.exception;

import kitchenpos.eatinorders.tobe.application.exceptions.OrderCustomException;
import kitchenpos.eatinorders.tobe.domain.handler.DomainExceptionHandler;
import kitchenpos.eatinorders.tobe.domain.model.ExceptionType;
import org.springframework.stereotype.Component;

@Component
public class CustomDomainExceptionHandler implements DomainExceptionHandler {
    @Override
    public void handle(ExceptionType type) {
        OrderCustomException.throwException(type);
    }
}
