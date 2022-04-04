package kitchenpos.menus.tobe.domain.model;

import kitchenpos.global.domain.vo.Price;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuProducts {

    private final List<MenuProduct> elements = new ArrayList<>();

    public MenuProducts(List<MenuProduct> products) {
        validate(products);
        this.elements.addAll(products);
    }

    private void validate(List<MenuProduct> elements) {
        if (elements.isEmpty()) {
            throw new IllegalArgumentException("반드시 하나 이상의 상품을 가져야 합니다.");
        }
    }

    public Price getTotalPrice() {
        return this.elements.stream()
                .map(MenuProduct::getSubTotalPrice)
                .reduce(Price.ZERO, Price::add);
    }
}
