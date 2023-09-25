package kitchenpos.eatinorders.application.exception;

import java.util.UUID;
import kitchenpos.eatinorders.domain.eatinorder.Status;

public class InvalidServeStatusException extends InvalidEatinOrderStatusException {

    public InvalidServeStatusException(final UUID id, final Status currentStatus) {
        super(id, currentStatus);
    }
}
