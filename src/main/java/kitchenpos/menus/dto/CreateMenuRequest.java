package kitchenpos.menus.dto;

import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.tobe.domain.ToBeMenuProduct;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class CreateMenuRequest {
    private final String name;
    private final BigDecimal price;
    private final UUID menuGroupId;
    private final boolean displayed;
    private final List<CreateMenuProductRequest> menuProducts;

    public CreateMenuRequest(String name, BigDecimal price, UUID menuGroupId, boolean displayed, List<CreateMenuProductRequest> menuProducts) {
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

    public boolean isDisplayed() {
        return displayed;
    }

    public List<CreateMenuProductRequest> getMenuProducts() {
        return menuProducts;
    }
}
