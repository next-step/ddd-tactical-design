package kitchenpos.eatinorder.tobe.domain.ordertable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class NumberOfGuests {
    private static final int DEFAULT = 0;

    @Column(name = "number_of_guests", nullable = false)
    private int num;

    protected NumberOfGuests() {
    }

    public static NumberOfGuests of(int num) {
        validate(num);
        return new NumberOfGuests(num);
    }

    private NumberOfGuests(int num) {
        this.num = num;
    }

    private static void validate(int num) {
        if (num < DEFAULT) {
            throw new IllegalArgumentException("손님 수는 0명보다 적을 수 없습니다.");
        }
    }

    public int getNum() {
        return num;
    }

    public NumberOfGuests cleared() {
        return NumberOfGuests.of(DEFAULT);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumberOfGuests that = (NumberOfGuests) o;
        return num == that.num;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(num);
    }
}
