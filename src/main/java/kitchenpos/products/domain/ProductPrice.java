package kitchenpos.products.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import kitchenpos.products.exception.ProductPriceException;

@Embeddable
public class ProductPrice {

    private static final BigDecimal MIN_PRICE = BigDecimal.ZERO;

    @Column(name = "price", nullable = false)
    private final BigDecimal price;

    public ProductPrice(BigDecimal price) {
        validatePrice(price);
        this.price = price;
    }

    private void validatePrice(BigDecimal price) {
        if (price.compareTo(MIN_PRICE) < 0 || Objects.isNull(price)) {
            throw new ProductPriceException(price);
        }
    }

    public ProductPrice changePrice(BigDecimal price) {
        validatePrice(price);
        return new ProductPrice(price);
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
        ProductPrice price1 = (ProductPrice) o;
        return Objects.equals(price, price1.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
