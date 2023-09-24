package kitchenpos.menus.application.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuCreateRequest {

    final private BigDecimal price;
    final private UUID menuGroupId;
    final private List<MenuProductRequest> menuProducts;
    final private String name;
    final private boolean displayed;

    public MenuCreateRequest(BigDecimal price, UUID menuGroupId, List<MenuProductRequest> menuProducts, String name, boolean displayed) {
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
        this.name = name;
        this.displayed = displayed;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProductRequest> getMenuProducts() {
        return menuProducts;
    }

    public String getName() {
        return name;
    }

    public boolean isDisplayed() {
        return displayed;
    }

}
