package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kitchenpos.products.exception.InvalidProductPriceException;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice {

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected ProductPrice() {
    }

    protected ProductPrice(BigDecimal price) {
        validatePrice(price);
        this.price = price;
    }

    public static ProductPrice from(long price) {
        return new ProductPrice(BigDecimal.valueOf(price));
    }

    public static ProductPrice from(BigDecimal price) {
        return new ProductPrice(price);
    }

    protected ProductPrice multiply(BigDecimal number) {
        return ProductPrice.from(price.multiply(number));
    }


    public BigDecimal priceValue() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPrice that = (ProductPrice) o;
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }

    private void validatePrice(BigDecimal price) {
        if (Objects.isNull(price) || isNegativeNumber(price)) {
            throw new InvalidProductPriceException(price);
        }
    }

    private boolean isNegativeNumber(BigDecimal price) {
        return price.compareTo(BigDecimal.ZERO) < 0;
    }
}
