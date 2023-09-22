package kitchenpos.product.tobe.domain;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProductPrice {

    @Column(name = "price", nullable = false)
    private BigDecimal value;

    protected ProductPrice() {
    }

    public ProductPrice(BigDecimal value) {
        if (Objects.isNull(value) || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("상품 가격은 없거나 0보다 작은 값으로 변경할 수 없습니다.");
        }

        this.value = value;
    }

    public BigDecimal getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductPrice)) {
            return false;
        }
        ProductPrice that = (ProductPrice) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public ProductPrice multiply(long quantity) {
        return new ProductPrice(this.value.multiply(BigDecimal.valueOf(quantity)));
    }
}
