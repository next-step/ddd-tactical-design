package kitchenpos.eatinorders.application.exception;

import java.util.UUID;
import kitchenpos.eatinorders.domain.eatinorder.Status;

public class InvalidAcceptStatusException extends InvalidEatinOrderStatusException {

    public InvalidAcceptStatusException(final UUID id, final Status currentStatus) {
        super(id, currentStatus);
    }
}
