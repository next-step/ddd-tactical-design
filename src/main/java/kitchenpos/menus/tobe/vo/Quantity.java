package kitchenpos.menus.tobe.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class Quantity {

    @Column(name = "quantity", nullable = false)
    private long value;

    protected Quantity() {

    }

    public Quantity(long value) {
        if (value < 0) {
            throw new IllegalArgumentException("수량을 확인해 주세요.");
        }
        this.value = value;
    }

    public BigDecimal toBigDecimal() {
        return BigDecimal.valueOf(value);
    }

    public long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Quantity quantity = (Quantity) o;

        return value == quantity.value;
    }

    @Override
    public int hashCode() {
        return (int) (value ^ (value >>> 32));
    }
}
