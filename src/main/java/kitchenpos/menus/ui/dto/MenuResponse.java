package kitchenpos.menus.ui.dto;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuProduct;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuResponse {

    private UUID id;

    private String name;

    private BigDecimal price;

    private boolean displayed;

    private List<MenuProduct> menuProducts;

    private MenuGroupResponse menuGroupResponse;

    public MenuResponse(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.displayed = menu.isDisplayed();
        this.menuProducts = menu.getMenuProducts();
        this.menuGroupResponse = new MenuGroupResponse(menu.getMenuGroup());
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

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }

    public MenuGroupResponse getMenuGroup() {
        return menuGroupResponse;
    }
}
