package kitchenpos.menus.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Quantity {

    @Column(name = "quantity", nullable = false)
    private Long value;

    protected Quantity() { }

    public Quantity(Long value) {
        if (value == null || value <= 0L) {
            throw new IllegalArgumentException("수량은 0개 이하가 될 수 없습니다.");
        }

        this.value = value;
    }

    public Long value() {
        return value;
    }

    public boolean isBiggerThen(kitchenpos.common.domain.Price price) {
        return this.value > price.value();
    }

    public boolean isBiggerThen(long value) {
        return isBiggerThen(new kitchenpos.common.domain.Price(value));
    }
}
