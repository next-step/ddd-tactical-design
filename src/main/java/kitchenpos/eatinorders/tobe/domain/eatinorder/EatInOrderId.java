package kitchenpos.eatinorders.tobe.domain.eatinorder;

import jakarta.persistence.Embeddable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public class EatInOrderId {

    public static final String ERROR_MESSAGE_VALUE_NULL = "값이 NULL 입니다.";

    private UUID value;

    public EatInOrderId(final UUID value) {
        validate(value);
        this.value = value;
    }

    protected EatInOrderId() {

    }

    private static void validate(final UUID value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException(ERROR_MESSAGE_VALUE_NULL);
        }
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        EatInOrderId that = (EatInOrderId) object;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
