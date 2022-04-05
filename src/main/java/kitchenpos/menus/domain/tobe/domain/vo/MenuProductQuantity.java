package kitchenpos.menus.domain.tobe.domain.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MenuProductQuantity {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuProductQuantity that = (MenuProductQuantity) o;

        return quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return (int) (quantity ^ (quantity >>> 32));
    }
}
