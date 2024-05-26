package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class Price {

    @Column(name = "price", columnDefinition = "decimal(19,2)", nullable = false)
    private BigDecimal value;

    protected Price() {
    }

    Price(final BigDecimal value) {
        if (value == null || isLessThanZero(value)) {
            throw new PriceLessThanZeroException("상품의 가격은 0원 이상이어야 합니다.");
        }
        this.value = value;
    }

    private boolean isLessThanZero(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) < 0;
    }
}
