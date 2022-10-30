package kitchenpos.menus.tobe.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MenuProductQuantity {
    public static final int MIN_QUANTITY = 0;

    @Column
    private long quantity;

    public MenuProductQuantity(long quantity) {
        if (quantity < MIN_QUANTITY) {
            throw new IllegalArgumentException();
        }

        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }

    protected MenuProductQuantity() {}
}
