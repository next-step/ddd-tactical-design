package kitchenpos.menus.tobe.domain.menu;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import kitchenpos.common.domain.Money;

public final class MenuProducts {

    private final List<MenuProduct> elements;

    public MenuProducts(List<MenuProduct> elements) {
        if (Objects.isNull(elements) || elements.isEmpty()) {
            throw new IllegalArgumentException(
                String.format("MenuProducts 는 비어 있을 수 없습니다. elements: %s", elements)
            );
        }
        this.elements = elements;
    }

    public MenuProducts(MenuProduct... elements) {
        this(Arrays.asList(elements));
    }

    public Money calculatePrice() {
        return elements.stream()
            .map(MenuProduct::calculatePrice)
            .reduce(Money.ZERO, Money::plus);
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
