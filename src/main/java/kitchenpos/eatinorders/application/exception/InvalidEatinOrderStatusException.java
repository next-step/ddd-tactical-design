package kitchenpos.eatinorders.application.exception;

import java.util.UUID;
import kitchenpos.eatinorders.domain.eatinorder.Status;

public class InvalidEatinOrderStatusException extends RuntimeException {

    public InvalidEatinOrderStatusException(final UUID id, final Status currentStatus) {
        super(String.format("invalid eatin order status. id: %s, currentStatus: %s", id,
            currentStatus));
    }
}
