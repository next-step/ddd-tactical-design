package kitchenpos.products.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class ProductPrice {
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected ProductPrice() {}

    public ProductPrice(final BigDecimal value) {
        validate(value);
        this.price = value;
    }

    public BigDecimal getPrice() {
        return price;
    }

    private void validate(final BigDecimal value) {
        if (Objects.isNull(value)) {
            throw new IllegalArgumentException("상품 가격은 필수값입니다.");
        }
        if (value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("상품 가격은 음수가 될 수 없습니다.");
        }
    }
}
