package kitchenpos.eatinorders.tobe.domain.order.menu;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class MenuProducts {

    private final List<MenuProduct> elements;

    public MenuProducts(List<MenuProduct> elements) {
        this.elements = elements;
    }

    public MenuProducts(MenuProduct... elements) {
        this.elements = Arrays.asList(elements);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MenuProducts)) {
            return false;
        }
        MenuProducts that = (MenuProducts) o;
        return Objects.equals(elements, that.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elements);
    }

    @Override
    public String toString() {
        return "MenuProducts{" +
            "elements=" + elements +
            '}';
    }
}
