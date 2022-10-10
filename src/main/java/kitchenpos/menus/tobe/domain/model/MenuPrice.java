package kitchenpos.menus.tobe.domain.model;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class MenuPrice {

    private long price;

    protected MenuPrice() {
    }

    public MenuPrice(long price) {
        validatePositive(price);
        this.price = price;
    }

    private void validatePositive(long price) {
        if (price < 0) {
            throw new IllegalArgumentException();
        }
    }

    boolean isGreaterThan(long amount) {
        return price > amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuPrice menuPrice = (MenuPrice) o;
        return Objects.equals(price, menuPrice.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}
