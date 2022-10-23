package kitchenpos.eatinorders.order.tobe.domain.vo;

import kitchenpos.common.domain.vo.Price;

import java.util.Objects;
import java.util.UUID;

public class MenuSpecification {

    private final UUID id;
    private final Price price;
    private final boolean displayed;

    public MenuSpecification(UUID id, Price price, boolean displayed) {
        this.id = id;
        this.price = price;
        this.displayed = displayed;
    }

    public UUID id() {
        return id;
    }

    public Price price() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuSpecification that = (MenuSpecification) o;
        return displayed == that.displayed && Objects.equals(id, that.id) && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, price, displayed);
    }
}
