package kitchenpos.eatinorders.tobe.domain;

import java.util.Objects;

public class NumberOfGuests {
    private static final int MIN = 0;
    private static final String INVALID_VALUE_MESSAGE = "잘못된 방문한 손님 수 입니다.";
    protected static final NumberOfGuests EMPTY = new NumberOfGuests(MIN);

    private final int value;

    public NumberOfGuests(int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < MIN) {
            throw new IllegalArgumentException(INVALID_VALUE_MESSAGE);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberOfGuests that = (NumberOfGuests) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
