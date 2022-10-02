package kitchenpos.products.tobe.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {
    private static final int MINIMUM_PRICE = 0;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected Price() {
    }

    public Price(int price) {
        validate(price);
        this.price = BigDecimal.valueOf(price);
    }

    private void validate(int price) {
        if (price < MINIMUM_PRICE) {
            throw new IllegalArgumentException("가격은 " + MINIMUM_PRICE + "원 이상이어야 합니다.");
        }
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Price price1 = (Price) o;
        return Objects.equals(price, price1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
