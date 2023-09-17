package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

import static java.util.Objects.isNull;

@Embeddable
public final class ProductPrice {
    @Column(name = "price", nullable = false)
    private BigDecimal value;

    protected ProductPrice() {
    }

    protected ProductPrice(BigDecimal value) {
        this.value = value;
    }

    public static ProductPrice create(BigDecimal value) {
        validateProductPriceIsNull(value);
        validateProductPriceIsNegative(value);

        return new ProductPrice(value);
    }

    private static void validateProductPriceIsNegative(BigDecimal value) {
        if (isNegative(value)) {
            throw new IllegalArgumentException("상품 가격은 음수일 수 없습니다.");
        }
    }

    private static void validateProductPriceIsNull(BigDecimal value) {
        if (isNull(value)) {
            throw new IllegalArgumentException("상품 가격은 비어있을 수 없습니다.");
        }
    }

    private static boolean isNegative(BigDecimal value) {
        return BigDecimal.ZERO.compareTo(value) > 0;
    }

    BigDecimal getValue() {
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
