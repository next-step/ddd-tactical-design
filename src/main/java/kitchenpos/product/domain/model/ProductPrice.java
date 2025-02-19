package kitchenpos.product.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Objects;

import static kitchenpos.product.exception.ProductExceptionMessage.PRODUCT_PRICE_CREATION_EXCEPTION;

@Embeddable
public class ProductPrice {
    @Column(name = "price", nullable = false)
    private final BigDecimal value;

    public ProductPrice(BigDecimal value) {
        validatePrice(value);
        this.value = value;
    }

    protected ProductPrice() {
        this.value = null;
    }

    private void validatePrice(BigDecimal value) {
        if (Objects.isNull(value) || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(PRODUCT_PRICE_CREATION_EXCEPTION.getMessage());
        }
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductPrice productPrice = (ProductPrice) o;
        return Objects.equals(value, productPrice.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}
