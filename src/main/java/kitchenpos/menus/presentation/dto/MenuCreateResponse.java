package kitchenpos.menus.presentation.dto;

import kitchenpos.common.vo.Price;
import kitchenpos.menus.tobe.domain.*;

public class MenuCreateResponse {

    private MenuId id;

    private MenuName name;

    private Price price;

    private MenuGroupId menuGroupId;

    private boolean isDisplayed;

    private MenuProducts products;

    public MenuCreateResponse(MenuId id, MenuName name, Price price, MenuGroupId menuGroupId, boolean isDisplayed, MenuProducts products) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.isDisplayed = isDisplayed;
        this.products = products;
    }

    public static MenuCreateResponse from(final Menu menu) {
        return new MenuCreateResponse(
                menu.getId(),
                menu.getName(),
                menu.getPrice(),
                menu.getGroupId(),
                menu.isDisplayed(),
                menu.getMenuProducts()
        );
    }

    public MenuId getId() {
        return id;
    }

    public MenuName getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public MenuGroupId getMenuGroupId() {
        return menuGroupId;
    }

    public boolean isDisplayed() {
        return isDisplayed;
    }

    public MenuProducts getProducts() {
        return products;
    }
}
