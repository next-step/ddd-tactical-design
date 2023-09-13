package kitchenpos.apply.products.tobe.domain;

import kitchenpos.support.domain.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
public class ProductPrice extends ValueObject {
    @Column(name = "price", nullable = false)
    private BigDecimal value;

    protected ProductPrice() { }

    public ProductPrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0 ) {
            throw new IllegalArgumentException("price는 0보다 높아야 한다");
        }
        this.value = price;
    }

    public BigDecimal value() {
        return value;
    }
}
