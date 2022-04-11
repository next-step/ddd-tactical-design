package kitchenpos.menus.tobe.dto;

import kitchenpos.common.domain.Quantity;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

public class MenuCreationRequest {
    private String name;
    private BigDecimal price;
    private UUID menuGroupId;
    private boolean displayed;
    private Map<UUID, Quantity> menuProducts;

    public MenuCreationRequest(String name, BigDecimal price, UUID menuGroupId, boolean displayed, Map<UUID, Quantity> menuProducts) {
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

    public Map<UUID, Quantity> getMenuProducts() {
        return menuProducts;
    }
}
