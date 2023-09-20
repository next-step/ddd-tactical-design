package kitchenpos.menus.tobe.dto;

import kitchenpos.menus.tobe.domain.MenuProduct;

import java.util.List;
import java.util.UUID;

public class MenuCreateRequestDto {
    private String name;
    private Long price;
    private UUID menuGroupId;
    private boolean displayed;
    private List<MenuProduct> menuProducts;

    public MenuCreateRequestDto(String name, Long price, UUID menuGroupId, boolean displayed, List<MenuProduct> menuProducts) {
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }
}
