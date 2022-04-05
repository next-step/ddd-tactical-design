package kitchenpos.menus.domain.tobe.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class MenuPrice {

    @Column(name = "price")
    private BigDecimal price;

    public MenuPrice(BigDecimal price) {
        this.price = price;
    }

    protected MenuPrice() {

    }

    public BigDecimal getValue() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuPrice that = (MenuPrice) o;

        return price != null ? price.equals(that.price) : that.price == null;
    }

    @Override
    public int hashCode() {
        return price != null ? price.hashCode() : 0;
    }
}
