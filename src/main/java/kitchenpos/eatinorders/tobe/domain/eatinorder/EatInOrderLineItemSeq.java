package kitchenpos.eatinorders.tobe.domain.eatinorder;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class EatInOrderLineItemSeq {

    public static final String ERROR_MESSAGE_VALUE_NULL = "값이 NULL 입니다.";

    private long value;

    public EatInOrderLineItemSeq(final Long value) {
        validate(value);
        this.value = value;
    }

    protected EatInOrderLineItemSeq() {

    }

    private static void validate(final Long value) {
        if (Objects.isNull(value)) {
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
        EatInOrderLineItemSeq that = (EatInOrderLineItemSeq) object;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
