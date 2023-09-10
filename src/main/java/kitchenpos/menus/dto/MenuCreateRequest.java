package kitchenpos.menus.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuCreateRequest {

    private BigDecimal price;
    private UUID menugroupId;
    private List<MenuProductRequest> menuProducts;
    private String name;
    private boolean displayed;

    public MenuCreateRequest(BigDecimal price, UUID menugroupId, List<MenuProductRequest> menuProducts, String name, boolean displayed) {
        this.price = price;
        this.menugroupId = menugroupId;
        this.menuProducts = menuProducts;
        this.name = name;
        this.displayed = displayed;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public UUID getMenugroupId() {
        return menugroupId;
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
