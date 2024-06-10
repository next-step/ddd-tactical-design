package kitchenpos.menus.tobe.domain.menuproduct;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.exception.IllegalQuantityException;

@Embeddable
public class Quantity {
    @Column(name = "quantity", nullable = false)
    private long value;

    protected Quantity() {
    }

    public static Quantity of(long quantity) {
        validateQuantity(quantity);
        return new Quantity(quantity);
    }

    public Quantity(long quantity) {
        this.value = quantity;
    }

    private static void validateQuantity(long quantity) {
        if (quantity < 0) {
            throw new IllegalQuantityException("수량은 0개보다 적을 수 없습니다.");
        }
    }

    public long getValue() {
        return value;
    }
}
