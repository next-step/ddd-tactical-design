package kitchenpos.eatinorders.tobe.domain.eatinorder;

import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
public class EatInOrderDateTime {

    public static final String ERROR_MESSAGE_VALUE_NULL = "값이 NULL 입니다.";

    private LocalDateTime value;

    public EatInOrderDateTime(final LocalDateTime value) {
        validate(value);
        this.value = value;
    }

    protected EatInOrderDateTime() {

    }

    private static void validate(final LocalDateTime value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException(ERROR_MESSAGE_VALUE_NULL);
        }
    }

    public LocalDateTime getValue() {
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
        EatInOrderDateTime that = (EatInOrderDateTime) object;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
