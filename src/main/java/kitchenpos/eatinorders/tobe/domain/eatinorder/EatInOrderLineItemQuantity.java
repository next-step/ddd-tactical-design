package kitchenpos.eatinorders.tobe.domain.eatinorder;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class EatInOrderLineItemQuantity {

    public static final String ERROR_MESSAGE_VALUE_NULL = "값이 NULL 이거나 마이너스 입니다.";
    public static final int MINIMUM_QUANTITY = 0;

    private long value;

    public EatInOrderLineItemQuantity(final Long value) {
        validate(value);
        this.value = value;
    }

    protected EatInOrderLineItemQuantity() {

    }

    private static void validate(final Long value) {
        if (value == null || value < MINIMUM_QUANTITY) {
            throw new IllegalArgumentException(ERROR_MESSAGE_VALUE_NULL);
        }
    }

    public long getValue() {
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
        EatInOrderLineItemQuantity that = (EatInOrderLineItemQuantity) object;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
