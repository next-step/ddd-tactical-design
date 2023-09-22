package kitchenpos.menus.application;

import java.util.List;
import java.util.UUID;

import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.Price;

public class CreateMenuRequest {
    private String name;
    private Price price;
    private UUID menuGroupId;
    private boolean displayed;
    private List<MenuProduct> menuProducts;

    public String getName() {
        return name;
    }

    public Price getPrice() {
        return price;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }
}
