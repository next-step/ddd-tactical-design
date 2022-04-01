package kitchenpos.menus.tobe.domain.model;

import kitchenpos.global.domain.vo.Price;
import kitchenpos.menus.tobe.domain.exception.IllegalMenuProductSizeException;

import java.util.Arrays;
import java.util.List;

public class MenuProducts {

    private final List<MenuProduct> elements;

    public MenuProducts(MenuProduct... elements) {
        validate(elements);
        this.elements = Arrays.asList(elements);
    }

    private void validate(MenuProduct[] elements) {
        if (elements.length == 0) {
            throw new IllegalMenuProductSizeException();
        }
    }

    public Price getTotalPrice() {
        return this.elements.stream()
                .map(MenuProduct::getSubTotalPrice)
                .reduce(Price.ZERO, Price::add);
    }
}
