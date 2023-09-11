package kitchenpos.menus.tobe.domain;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MenuPrice {

    private BigDecimal price;

    public MenuPrice() {
    }

    public MenuPrice(final BigDecimal price) {
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        this.price = price;
    }

    public BigDecimal toBigDecimal() {
        return new BigDecimal(price.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuPrice productPrice1 = (MenuPrice) o;

        return price.equals(productPrice1.price);
    }

    @Override
    public int hashCode() {
        return price.hashCode();
    }
}
