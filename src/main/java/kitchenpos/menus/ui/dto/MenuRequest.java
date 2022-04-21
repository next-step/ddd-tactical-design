package kitchenpos.menus.ui.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuRequest {

    private String name;

    private BigDecimal price;

    private UUID menuGroupId;

    private boolean displayed;

    private List<MenuProductRequest> menuProducts;

    public MenuRequest(BigDecimal price) {
        this.price = price;
    }

    public MenuRequest(String name, BigDecimal price, UUID menuGroupId, boolean displayed, List<MenuProductRequest> menuProducts) {
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
