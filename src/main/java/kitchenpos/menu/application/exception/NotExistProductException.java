package kitchenpos.menu.application.exception;

import com.google.common.base.MoreObjects;
import java.util.UUID;

public class NotExistProductException extends RuntimeException {

    private final UUID id;

    public NotExistProductException(final UUID id) {
        super(String.format("not exist product. id: %s", id));

        this.id = id;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
            .add("id", id)
            .toString();
    }
}
