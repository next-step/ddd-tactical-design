package kitchenpos.menus.tobe.domain;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MenuPrice {

    private BigDecimal price;

    protected MenuPrice() {
    }

    public MenuPrice(long price) {
        this(BigDecimal.valueOf(price));
    }

    public MenuPrice(MenuPrice price) {
        this(price.price);
    }

    public MenuPrice(BigDecimal price) {
        if(price == null || BigDecimal.ZERO.compareTo(price) > 0) {
            throw new IllegalArgumentException();
        }
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuPrice price1 = (MenuPrice) o;

        return Objects.equals(price, price1.price);
    }

    @Override
    public int hashCode() {
        return price != null ? price.hashCode() : 0;
    }

    public BigDecimal value() {
        return price;
    }
}
