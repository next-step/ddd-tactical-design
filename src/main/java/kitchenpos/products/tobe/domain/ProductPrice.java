package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice {
    public static final ProductPrice ZERO = ProductPrice.from(BigDecimal.ZERO);
    @Column(name = "price", nullable = false)
    private BigDecimal value;

    protected ProductPrice() {
    }

    private ProductPrice(BigDecimal value) {
        this.validate(value);
        this.value = value;
    }

    private void validate(BigDecimal value) {
        if (Objects.isNull(value) || this.isNegativeNumber(value)) {
            throw new IllegalArgumentException();
        }
    }

    private boolean isNegativeNumber(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) < 0;
    }

    public static ProductPrice from(BigDecimal value) {
        return new ProductPrice(value);
    }

    public ProductPrice add(ProductPrice productPrice) {
        return new ProductPrice(this.value.add(productPrice.value));
    }

    public ProductPrice multiplyQuantity(BigDecimal quantity) {
        return new ProductPrice(this.value.multiply(quantity));
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductPrice that = (ProductPrice) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
