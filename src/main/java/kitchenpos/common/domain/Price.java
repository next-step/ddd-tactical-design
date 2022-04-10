package kitchenpos.common.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Price {

    @Column(name = "price", nullable = false)
    private Long value;

    protected Price() {
    }

    public Price(Long value) {
        if (value == null || value <= 0L) {
            throw new IllegalArgumentException("가격은 0원 이하가 될 수 없습니다.");
        }

        this.value = value;
    }

    public Long value() {
        return value;
    }

    public boolean isBiggerThen(Price price) {
        return this.value > price.value();
    }

    public boolean isBiggerThen(long value) {
        return isBiggerThen(new Price(value));
    }
}
