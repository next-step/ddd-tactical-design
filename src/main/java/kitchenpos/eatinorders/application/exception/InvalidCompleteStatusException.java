package kitchenpos.eatinorders.application.exception;

import java.util.UUID;
import kitchenpos.eatinorders.domain.eatinorder.Status;

public class InvalidCompleteStatusException extends InvalidEatinOrderStatusException {

    public InvalidCompleteStatusException(final UUID id, final Status currentStatus) {
        super(id, currentStatus);
    }
}
