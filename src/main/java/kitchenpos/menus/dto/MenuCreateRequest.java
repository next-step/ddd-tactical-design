package kitchenpos.menus.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public final class MenuCreateRequest {
    private final String name;
    private final BigDecimal price;
    private final UUID menuGroupId;
    private final Boolean displayed;
    private final List<MenuProductElement> menuProducts;

    public MenuCreateRequest(String name, BigDecimal price, UUID menuGroupId, Boolean displayed, List<MenuProductElement> menuProducts) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public Boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProductElement> getMenuProducts() {
        return menuProducts;
    }

}
