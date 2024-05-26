package kitchenpos.products.tobe.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class Price {

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public Price(long price) {
        this.price = validate(BigDecimal.valueOf(price));
    }

    public Price() {}

    private BigDecimal validate(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("가격은 0 이상이어야 합니다.");
        }
        return price;
    }

    public BigDecimal getPrice() {
        return price;
    }

}
