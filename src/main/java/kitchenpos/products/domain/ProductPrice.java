package kitchenpos.products.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice {
    private static final BigDecimal MIN_PRICE = BigDecimal.ZERO;
    private static final String INVALID_PRICE_MESSAGE = "상품의 가격은 0원 이상이어야 합니다.";

    @Column(name = "price", nullable = false)
    private BigDecimal value;

    protected ProductPrice() {

    }

    public ProductPrice(BigDecimal value) {
        validate(value);
        this.value = value;
    }

    private void validate(BigDecimal price) {
        if (Objects.isNull(price) || MIN_PRICE.compareTo(price) > 0) {
            throw new IllegalArgumentException(INVALID_PRICE_MESSAGE);
        }
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
