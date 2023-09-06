package kitchenpos.products.tobe.domain;

import kitchenpos.products.exception.ProductErrorCode;
import kitchenpos.products.exception.ProductPriceException;

import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;


@Embeddable
public class ProductPrice {
    private BigDecimal price;

    protected ProductPrice() {

    }

    public ProductPrice(BigDecimal price) {
        if (price == null) {
            throw new ProductPriceException(ProductErrorCode.PRICE_IS_NULL);
        }
        if (isLessThanZero(price)) {
            throw new ProductPriceException(ProductErrorCode.PRICE_IS_GREATER_THAN_EQUAL_ZERO);
        }
        this.price = price;
    }

    private boolean isLessThanZero(BigDecimal price) {
        return price.compareTo(BigDecimal.ZERO) < 0;
    }

    public ProductPrice add(ProductPrice inputPrice) {
        BigDecimal add = price.add(inputPrice.price);
        return new ProductPrice(add);
    }

    public ProductPrice multiply(long input) {
        BigDecimal multiply = price.multiply(BigDecimal.valueOf(input));
        return new ProductPrice(multiply);
    }

    public BigDecimal getValue() {
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
}


