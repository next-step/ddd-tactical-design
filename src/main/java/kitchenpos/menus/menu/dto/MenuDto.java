package kitchenpos.menus.menu.dto;

import java.util.List;
import java.util.UUID;

public class MenuDto {

    private String name;
    private Long price;
    private UUID menuGroupId;
    private Boolean displayed;
    private List<MenuProductDto> menuProducts;

    public MenuDto(String name, Long price, UUID menuGroupId, Boolean displayed, List<MenuProductDto> menuProducts) {
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

    public Boolean getDisplayed() {
        return displayed;
    }

    public List<MenuProductDto> getMenuProducts() {
        return menuProducts;
    }
}
