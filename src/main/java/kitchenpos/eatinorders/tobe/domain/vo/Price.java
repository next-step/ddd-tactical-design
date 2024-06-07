package kitchenpos.eatinorders.tobe.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected Price() {}

    public Price(BigDecimal price) {
        checkPrice(price);
        this.price = price;
    }

    private void checkPrice(BigDecimal price) {
        if (Objects.isNull(price)) {
            throw new IllegalArgumentException("주문 항목 금액이 존재하지 않습니다.");
        }
        if (price.signum() == -1) {
            throw new IllegalArgumentException("주문 항목 금액이 음수입니다.");
        }
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isNotSamePrice(Price price) {
        return !isSamePrice(price);
    }

    public boolean isSamePrice(Price price) {
        return this.equals(price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return Objects.equals(price, price1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
