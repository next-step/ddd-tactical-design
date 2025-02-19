package kitchenpos.menu.application.service.model;

import kitchenpos.menu.domain.model.MenuGroup;
import kitchenpos.menu.domain.model.MenuProduct;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class CreateMenuRequest {
    private UUID id;
    private String name;
    private BigDecimal price;
    private MenuGroup menuGroup;
    private boolean displayed;
    private List<CreateMenuProductRequest> menuProducts;
    private UUID menuGroupId;

    public CreateMenuRequest() {
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

    public MenuGroup getMenuGroup() {
        return menuGroup;
    }

    public void setMenuGroup(final MenuGroup menuGroup) {
        this.menuGroup = menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(final boolean displayed) {
        this.displayed = displayed;
    }

    public List<CreateMenuProductRequest> getMenuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(final List<CreateMenuProductRequest> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public void setMenuGroupId(final UUID menuGroupId) {
        this.menuGroupId = menuGroupId;
    }
}
