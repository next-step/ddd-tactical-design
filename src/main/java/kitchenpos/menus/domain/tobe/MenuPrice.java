package kitchenpos.menus.domain.tobe;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MenuPrice {
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected MenuPrice() {
    }

    public MenuPrice(BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        this.price = price;
    }

    public static MenuPrice valueOf(int value) {
        return new MenuPrice(BigDecimal.valueOf(value));
    }

    public static MenuPrice valueOf(BigDecimal value) {
        return new MenuPrice(value);
    }

    public MenuPrice add(MenuPrice value) {
        return add(value);
    }

    public MenuPrice add(BigDecimal value) {
        return new MenuPrice(price.add(value));
    }

    public MenuPrice multiply(MenuPrice value) {
        return multiply(value.price);
    }

    public MenuPrice multiply(BigDecimal value) {
        return new MenuPrice(price.multiply(value));
    }

    public boolean isBiggerPrice(BigDecimal value) {
        return price.compareTo(value) > 0;
    }

    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuPrice)) {
            return false;
        }
        MenuPrice menuPrice = (MenuPrice) o;
        return Objects.equals(price, menuPrice.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
