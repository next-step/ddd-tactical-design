package kitchenpos.menus.tobe.domain;

import javax.persistence.Embeddable;

@Embeddable
public class MenuQuantity {

    private long quantity;

    public MenuQuantity() {
    }

    public MenuQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuQuantity that = (MenuQuantity) o;

        return quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return (int) (quantity ^ (quantity >>> 32));
    }

    public long value() {
        return quantity;
    }

    public boolean lessThenOne() {
        return quantity < 1;
    }


}
