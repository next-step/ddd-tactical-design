package kitchenpos.menus.application.dto;

import kitchenpos.menus.tobe.domain.menu.MenuId;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MenuResponse {

    final private UUID id;

    final private String name;

    final private BigDecimal price;

    final private UUID menuGroupId;

    final private boolean displayed;

    final private List<MenuProductResponse> menuProducts;

    public MenuResponse(UUID id, String name, BigDecimal price, UUID menuGroupId, boolean displayed, List<MenuProductResponse> menuProducts) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }

    public MenuResponse(MenuId id, String name, BigDecimal price, UUID menuGroupId, boolean displayed, List<MenuProductResponse> menuProducts) {
        this.id = id.getValue();
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
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

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public List<MenuProductResponse> getMenuProducts() {
        return Collections.unmodifiableList(menuProducts);
    }

}
