package kitchenpos.menus.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Embeddable
public class MenuPrice {

    private static final int COMPARE_ZERO = 0;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected MenuPrice() {
    }

    public MenuPrice(BigDecimal price) {
        validatePrice(price);
        this.price = price;
    }

    private void validate(BigDecimal price, MenuProducts menuProducts) {
        validatePrice(price);
        validateOverMenuProductsTotalPrice(price, menuProducts);
    }

    private void validatePrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < COMPARE_ZERO) {
            throw new IllegalArgumentException();
        }
    }

    private void validateOverMenuProductsTotalPrice(BigDecimal price, MenuProducts menuProducts) {
        if (isOverMenuProductsTotalPrice(price, menuProducts)) {
            throw new IllegalArgumentException();
        }
    }

    public void changePrice(BigDecimal price, MenuProducts menuProducts) {
        validate(price, menuProducts);
        this.price = price;
    }

    public boolean isOverMenuProductsTotalPrice(MenuProducts menuProducts) {
        return isOverMenuProductsTotalPrice(this.price, menuProducts);
    }

    private boolean isOverMenuProductsTotalPrice(BigDecimal price, MenuProducts menuProducts) {
        return price.compareTo(menuProducts.totalPrice()) > COMPARE_ZERO;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuPrice)) return false;
        MenuPrice menuPrice = (MenuPrice) o;
        return Objects.equals(price, menuPrice.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
