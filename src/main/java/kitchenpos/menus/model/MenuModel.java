package kitchenpos.menus.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class MenuModel {
    private UUID id;
    private String name;
    private BigDecimal price;
    private MenuGroupModel menuGroup;
    private boolean displayed;
    List<MenuProductModel> menuProducts;
    private UUID menuGroupId;

    public MenuModel(UUID id, String name, BigDecimal price, MenuGroupModel menuGroup, boolean displayed, List<MenuProductModel> menuProducts, UUID menuGroupId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
        this.menuGroupId = menuGroupId;
    }

    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal price() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public MenuGroupModel menuGroup() {
        return menuGroup;
    }

    public void setMenuGroup(MenuGroupModel menuGroup) {
        this.menuGroup = menuGroup;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

    public List<MenuProductModel> menuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(List<MenuProductModel> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public UUID menuGroupId() {
        return menuGroupId;
    }

    public void setMenuGroupId(UUID menuGroupId) {
        this.menuGroupId = menuGroupId;
    }
}
