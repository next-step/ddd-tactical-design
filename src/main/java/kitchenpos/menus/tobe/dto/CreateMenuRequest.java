package kitchenpos.menus.tobe.dto;

import kitchenpos.menuproducts.dto.MenuProductRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class CreateMenuRequest {
    private String name;
    private BigDecimal price;
    private UUID menuGroupId;
    private boolean displayed;
    private List<MenuProductRequest> menuProducts;

    public CreateMenuRequest() {}

    public CreateMenuRequest(final String name, final BigDecimal price, final UUID menuGroupId, final boolean displayed, final List<MenuProductRequest> menuProducts) {
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

    public List<MenuProductRequest> getMenuProducts() {
        return menuProducts;
    }
}
