package kitchenpos.menus.tobe.ui;

import kitchenpos.menus.tobe.domain.MenuProducts;
import kitchenpos.menus.tobe.domain.TobeMenu;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MenuForm {
    private UUID id;
    private String name;
    private BigDecimal price;
    private MenuGroupForm menuGroup;
    private UUID menuGroupId;
    private boolean displayed;
    private List<MenuProductForm> menuProducts;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public MenuGroupForm getMenuGroup() {
        return menuGroup;
    }

    public void setMenuGroup(MenuGroupForm menuGroup) {
        this.menuGroup = menuGroup;
    }

    public UUID getMenuGroupId() {
        return menuGroupId;
    }

    public void setMenuGroupId(UUID menuGroupId) {
        this.menuGroupId = menuGroupId;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

    public List<MenuProductForm> getMenuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(List<MenuProductForm> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public static MenuForm of(TobeMenu menu) {
        MenuForm menuForm = new MenuForm();
        menuForm.setName(menu.getName());
        menuForm.setPrice(menu.getPrice());
        menuForm.setDisplayed(menu.isDisplayed());
        menuForm.setMenuGroup(MenuGroupForm.of(menu.getMenuGroup()));
        menuForm.setMenuProducts(menuProductToMenuProductForm(menu.getMenuProducts()));
        return menuForm;
    }

    private static List<MenuProductForm> menuProductToMenuProductForm(MenuProducts menuProducts) {
        return menuProducts.getMenuProducts()
                .stream()
                .map(MenuProductForm::of)
                .collect(Collectors.toList());
    }
}
