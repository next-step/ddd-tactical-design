package kitchenpos.menus.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public final class MenuDetailResponse {
    private final UUID id;
    private final String name;
    private final BigDecimal price;
    private final MenuGroupDetailResponse menuGroup;
    private final Boolean displayed;
    private final List<MenuProductElement> menuProducts;

    public MenuDetailResponse(UUID id, String name, BigDecimal price, MenuGroupDetailResponse menuGroup, Boolean displayed, List<MenuProductElement> menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public MenuGroupDetailResponse getMenuGroup() {
        return menuGroup;
    }

    public Boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProductElement> getMenuProducts() {
        return menuProducts;
    }
}
