package kitchenpos.menus.tobe.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MenuPrice {

    @Column(name = "price", nullable = false)
    private Long value;

    protected MenuPrice() {
    }

    public MenuPrice(Long value) {
        if (value == null || value <= 0L) {
            throw new IllegalArgumentException("가격은 0원 이하가 될 수 없습니다.");
        }

        this.value = value;
    }

    public Long value() {
        return value;
    }

    public boolean isBiggerThen(MenuPrice price) {
        return this.value > price.value();
    }

    public boolean isBiggerThen(long value) {
        return isBiggerThen(new MenuPrice(value));
    }
}
