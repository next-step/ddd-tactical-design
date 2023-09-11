package kitchenpos.products.tobe.domain;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice {

    private BigDecimal price;

    public ProductPrice() {
    }

    public ProductPrice(BigDecimal price) {
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

        ProductPrice productPrice1 = (ProductPrice) o;

        return price.equals(productPrice1.price);
    }

    @Override
    public int hashCode() {
        return price.hashCode();
    }
}
