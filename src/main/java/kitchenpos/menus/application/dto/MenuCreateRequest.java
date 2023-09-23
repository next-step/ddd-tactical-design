package kitchenpos.menus.application.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuCreateRequest {

    private BigDecimal price;
    private UUID menuGroupId;
    private List<MenuProductRequest> menuProducts;
    private String name;
    private boolean displayed;

    public MenuCreateRequest() {
    }

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

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setMenuGroupId(UUID menuGroupId) {
        this.menuGroupId = menuGroupId;
    }

    public void setMenuProducts(List<MenuProductRequest> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }
}
