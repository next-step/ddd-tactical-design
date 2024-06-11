package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Price {

    @Column(name = "price", nullable = false)
    private Long value;

    protected Price() {
        // JPA를 위한 기본 생성자
    }

    public Price(Long value) {
        validatePrice(value);
        this.value = value;
    }

    public Long getValue() {
        return value;
    }

    private void validatePrice(Long value) {
        if (value == null || value < 0) {
            throw new IllegalArgumentException("가격을 입력하지 않거나 음수를 입력할 수 없습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price = (Price) o;
        return Objects.equals(getValue(), price.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
