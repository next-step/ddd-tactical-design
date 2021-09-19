package kitchenpos.eatinorders.tobe.domain.model;

public class NumberOfGuests {

    private final long value;

    public NumberOfGuests(final long value) {
        validate(value);

        this.value = value;
    }

    private void validate(final long value) {
        if (value < 0L) {
            throw new IllegalArgumentException("방문한 손님 수는 0 이상이어야 합니다.");
        }
    }

    public long value() {
        return value;
    }
}
