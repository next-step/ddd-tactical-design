package kitchenpos.menus.tobe.domain;

import javax.persistence.Embeddable;

@Embeddable
public class MenuProductQuantity {
    private long value;

    protected MenuProductQuantity() { }

    public MenuProductQuantity(long quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("quantity는 0보다 높아야 한다");
        }
        this.value = quantity;
    }

    public long value() {
        return value;
    }
}
