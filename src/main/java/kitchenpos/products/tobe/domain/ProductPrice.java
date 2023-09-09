package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Embeddable;
import kitchenpos.products.exception.ProductErrorCode;
import kitchenpos.products.exception.ProductException;

@Embeddable
public class ProductPrice {

    BigDecimal price;

    protected ProductPrice() {
    }

    public ProductPrice(BigDecimal price) {
        if (isPriceNegative(price)) {
            throw new ProductException(ProductErrorCode.PRICE_IS_NEGATIVE);
        }
        if (isPriceNull(price)) {
            throw new ProductException(ProductErrorCode.PRICE_IS_NULL);
        }
        this.price = price;
    }

    private boolean isPriceNegative(BigDecimal price) {
        return price.compareTo(BigDecimal.ZERO) < 0;
    }

    private boolean isPriceNull(BigDecimal price) {
        return price == null;
    }

    private ProductPrice addPrice(BigDecimal addPrice) {
        return new ProductPrice(price.add(addPrice));
    }

    public BigDecimal getValue() {
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
        ProductPrice that = (ProductPrice) o;
        return Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
