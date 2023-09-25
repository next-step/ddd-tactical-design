package kitchenpos.eatinorders.application.exception;

import com.google.common.base.MoreObjects;
import java.util.UUID;

public final class NotExistOrderTableException extends RuntimeException {

    private final UUID id;

    public NotExistOrderTableException(final UUID id) {
        super(String.format("not exist orderTable. id: %s", id));

        this.id = id;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("id", id)
            .toString();
    }
}
