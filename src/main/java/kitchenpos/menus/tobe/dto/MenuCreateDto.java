package kitchenpos.menus.tobe.dto;

import kitchenpos.menus.tobe.domain.entity.MenuProduct;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuCreateDto {
    private String name;
    private BigDecimal price;
    private boolean isDisplayed;
    private UUID menuGroupId;
    private List<MenuProduct> menuProducts;

    public MenuCreateDto(String name, BigDecimal price, boolean isDisplayed, UUID menuGroupId, List<MenuProduct> menuProducts) {
        this.name = name;
        this.price = price;
        this.isDisplayed = isDisplayed;
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
        return isDisplayed;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }
}
