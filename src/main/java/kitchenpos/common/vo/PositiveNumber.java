package kitchenpos.common.vo;

import jakarta.persistence.Embeddable;
import kitchenpos.common.exception.NegativeNumberException;

import java.util.Objects;

@Embeddable
public class PositiveNumber {

    private int value;

    protected PositiveNumber() {
    }

    public PositiveNumber(final int value) {
        validate(value);
        this.value = value;
    }

    private void validate(int value) {
        if (value < 0) {
            throw new NegativeNumberException("양수만 입력 가능합니다");
        }
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PositiveNumber that = (PositiveNumber) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
