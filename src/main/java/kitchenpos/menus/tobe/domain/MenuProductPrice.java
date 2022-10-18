package kitchenpos.menus.tobe.domain;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MenuProductPrice {

    private BigDecimal price;

    protected MenuProductPrice() {
    }

    public MenuProductPrice(long price) {
        this(BigDecimal.valueOf(price));
    }

    public MenuProductPrice(MenuProductPrice price) {
        this(price.price);
    }

    public MenuProductPrice(BigDecimal price) {
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

        MenuProductPrice price1 = (MenuProductPrice) o;

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
