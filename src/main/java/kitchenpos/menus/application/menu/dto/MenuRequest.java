package kitchenpos.menus.application.menu.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuRequest {
    private final String name;

    private final BigDecimal price;

    private final UUID menuGroupId;

    private final boolean displayed;

    private final List<MenuProductRequest> menuProductsRequests;

    public MenuRequest(final String name, final BigDecimal price, final UUID menuGroupId, final boolean displayed, final List<MenuProductRequest> menuProductsRequests) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProductsRequests = menuProductsRequests;
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

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProductRequest> getMenuProductsRequests() {
        return menuProductsRequests;
    }
}
