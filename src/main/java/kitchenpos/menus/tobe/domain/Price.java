package kitchenpos.menus.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {

    private static final int COMPARE_ZERO = 0;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected Price() {
    }

    public Price(BigDecimal price) {
        validatePrice(price);
        this.price = price;
    }

    private void validatePrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < COMPARE_ZERO) {
            throw new IllegalArgumentException();
        }
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isOver(BigDecimal price) {
        return this.price.compareTo(price) > COMPARE_ZERO;
    }

    public boolean isOverMenuProductsTotalPrice(MenuProducts menuProducts) {
        return price.compareTo(menuProducts.totalPrice()) > COMPARE_ZERO;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Price)) return false;
        Price price = (Price) o;
        return Objects.equals(this.price, price.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }


}
