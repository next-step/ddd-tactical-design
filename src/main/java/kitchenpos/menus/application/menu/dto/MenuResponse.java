package kitchenpos.menus.application.menu.dto;

import kitchenpos.menus.domain.menu.Menu;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuResponse {
    private final UUID id;

    private final String name;

    private final BigDecimal price;

    private final UUID menuGroupId;

    private final boolean displayed;

    private final List<MenuProductResponse> menuProductsResponse;

    public MenuResponse(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.price = menu.getPrice();
        this.menuGroupId = menu.getMenuGroupId();
        this.displayed = menu.isDisplayed();
        this.menuProductsResponse = MenuProductResponse.ofList(menu.getMenuProducts());
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

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProductResponse> getMenuProductsResponse() {
        return menuProductsResponse;
    }
}
