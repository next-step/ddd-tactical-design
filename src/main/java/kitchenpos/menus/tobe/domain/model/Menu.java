package kitchenpos.menus.tobe.domain.model;

import kitchenpos.global.domain.vo.DisplayName;
import kitchenpos.global.domain.vo.Price;

import java.util.UUID;

public final class Menu {

    private UUID id;
    private MenuGroup group;
    private DisplayName name;
    private Price price;
    private MenuProducts menuProducts;
    private boolean displayed;

    public boolean isDisplayed() {
        return displayed && !price.lessThanEquals(menuProducts.getTotalPrice());
    }
}
