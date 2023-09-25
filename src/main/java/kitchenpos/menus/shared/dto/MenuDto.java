package kitchenpos.menus.shared.dto;

import kitchenpos.menus.tobe.domain.menu.MenuProduct;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuDto {
    private UUID id;
    private String name;
    private BigDecimal price;
    private MenuGroupDto menuGroup;
    private boolean displayed;
    private List<MenuProductDto> menuProducts;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MenuGroupDto getMenuGroup() {
        return menuGroup;
    }

    public List<MenuProductDto> getMenuProducts() {
        return menuProducts;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
