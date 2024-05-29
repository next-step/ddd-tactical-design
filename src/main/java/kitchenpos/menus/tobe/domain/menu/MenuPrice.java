package kitchenpos.menus.tobe.domain.menu;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MenuPrice {

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected MenuPrice() {
    }

    protected MenuPrice(BigDecimal price) {
        validatePrice(price);
        this.price = price;
    }

    public static MenuPrice from(long price) {
        return new MenuPrice(BigDecimal.valueOf(price));
    }

    public static MenuPrice from(BigDecimal price) {
        return new MenuPrice(price);
    }

    public BigDecimal priceValue() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuPrice that = (MenuPrice) o;
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }

    private void validatePrice(BigDecimal price) {
        if (Objects.isNull(price) || isNegativeNumber(price)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean isNegativeNumber(BigDecimal price) {
        return price.compareTo(BigDecimal.ZERO) < 0;
    }
}
