package kitchenpos.menus.dto;

import kitchenpos.menus.tobe.domain.menu.MenuId;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class MenuResponse {

    private UUID id;

    private String name;

    private BigDecimal price;

    private UUID menuGroupId;

    private boolean displayed;

    private List<MenuProductResponse> menuProducts;

    public MenuResponse() {
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