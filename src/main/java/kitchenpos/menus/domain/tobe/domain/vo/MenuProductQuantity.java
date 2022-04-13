package kitchenpos.menus.domain.tobe.domain.vo;

import kitchenpos.support.vo.ValueObject;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MenuProductQuantity extends ValueObject<MenuProductQuantity> {

    @Column(name = "quantity")
    private long quantity;

    public MenuProductQuantity(long quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException();
        }
        this.quantity = quantity;
    }

    protected MenuProductQuantity() {

    }

    public long getValue() {
        return quantity;
    }

}
