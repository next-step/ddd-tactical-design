package kitchenpos.eatinorder.domain.model.todo;

public class NumberOfGuests {
    private final int value;

    private NumberOfGuests(final int value) {
        this.value = value;
    }

    public static NumberOfGuests of(final int value) {
        if (value < 0) {
            throw new IllegalArgumentException("손님 수는 0 미만일 수 없습니다.");
        }
        return new NumberOfGuests(value);
    }

    public int value() {
        return value;
    }
}
