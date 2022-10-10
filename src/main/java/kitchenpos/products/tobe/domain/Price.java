package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Price {
    private static final long MIN_PRICE = 0;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public Price(long price) {
        validate(price);
        this.price = BigDecimal.valueOf(price);
    }

    private void validate(long price) {
        if (price < MIN_PRICE) {
            throw new IllegalArgumentException("가격은 0원 미만 일 수 없다.");
        }
    }

}
