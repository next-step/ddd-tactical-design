package kitchenpos.products.tobe.domain;

import kitchenpos.products.tobe.support.Value;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;
import java.util.Objects;

@Embeddable
public class MenuPrice extends Value<MenuPrice> {

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    protected MenuPrice() {
    }

    public MenuPrice(final BigDecimal price) {
        validate(price);
        this.price = price;
    }

    private void validate(BigDecimal value) {
        if (Objects.isNull(value) || value.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("가격은 비어있거나 음수가 될 수 없습니다.");
        }
    }


    public boolean isGreaterThan(final int price) {
        return this.price.intValue() > price;
    }
}
