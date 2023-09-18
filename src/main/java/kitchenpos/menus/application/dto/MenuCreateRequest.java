package kitchenpos.menus.application.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuCreateRequest {

    private final String name;
    private final BigDecimal price;
    private final boolean displayed;
    private final UUID menuGroupId;
    private final List<MenuProductCreateRequest> menuProducts;

    public MenuCreateRequest(String name, BigDecimal price, boolean displayed, UUID menuGroupId, List<MenuProductCreateRequest> menuProducts) {
        this.name = name;
        this.price = price;
        this.displayed = displayed;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProductCreateRequest> getMenuProducts() {
        return menuProducts;
    }
}
