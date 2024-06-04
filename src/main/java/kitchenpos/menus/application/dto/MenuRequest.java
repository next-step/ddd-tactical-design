package kitchenpos.menus.application.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuRequest {
    private UUID id;
    private String name;
    private BigDecimal price;

    private MenuGroupRequest menuGroupRequest;

    private boolean displayed;

    private List<MenuProductRequest> menuProducts;

    private UUID menuGroupId;

    public MenuRequest() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }

    public MenuGroupRequest getMenuGroup() {
        return menuGroupRequest;
    }

    public void setMenuGroup(final MenuGroupRequest menuGroupRequest) {
        this.menuGroupRequest = menuGroupRequest;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(final boolean displayed) {
        this.displayed = displayed;
    }

    public List<MenuProductRequest> getMenuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(final List<MenuProductRequest> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public void setMenuGroupId(final UUID menuGroupId) {
        this.menuGroupId = menuGroupId;
    }
}
