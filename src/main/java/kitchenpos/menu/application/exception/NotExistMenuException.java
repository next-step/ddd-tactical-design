package kitchenpos.menu.application.exception;

import com.google.common.base.MoreObjects;
import java.util.UUID;

public final class NotExistMenuException extends RuntimeException {

    private final UUID id;

    public NotExistMenuException(final UUID id) {
        super(String.format("not exist menu. id: %s", id));

        this.id = id;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("id", id)
            .toString();
    }
}
