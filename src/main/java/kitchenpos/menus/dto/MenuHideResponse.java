package kitchenpos.menus.dto;

import kitchenpos.menus.domain.MenuProducts;
import kitchenpos.menus.domain.tobe.domain.TobeMenu;
import kitchenpos.menus.domain.tobe.domain.TobeMenuProduct;
import kitchenpos.menus.domain.tobe.domain.vo.MenuGroupId;
import kitchenpos.menus.domain.tobe.domain.vo.MenuId;
import kitchenpos.support.dto.DTO;

import java.math.BigDecimal;
import java.util.List;

public class MenuHideResponse extends DTO {
    private MenuId menuId;
    private String name;
    private BigDecimal price;
    private MenuGroupId menuGroupId;
    private MenuProducts menuProducts;
    private boolean displayed;

    public MenuHideResponse() {
    }

    public MenuHideResponse(MenuId menuId, String name, BigDecimal price, MenuGroupId menuGroupId, MenuProducts menuProducts, boolean displayed) {
        this.menuId = menuId;
        this.name = name;
        this.price = price;
        this.menuGroupId = menuGroupId;
        this.menuProducts = menuProducts;
        this.displayed = displayed;
    }

    public MenuHideResponse(TobeMenu menu) {
        this.menuId = menu.getId();
        this.name = menu.getName().getValue();
        this.price = menu.getPrice().getValue();
        this.menuGroupId = menu.getMenuGroupId();
        this.menuProducts = menu.getMenuProducts();
        this.displayed = menu.getDisplayed().getValue();
    }

    public MenuId getMenuId() {
        return menuId;
    }

    public void setMenuId(MenuId menuId) {
        this.menuId = menuId;
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

    public MenuGroupId getMenuGroupId() {
        return menuGroupId;
    }

    public void setMenuGroupId(MenuGroupId menuGroupId) {
        this.menuGroupId = menuGroupId;
    }

    public MenuProducts getMenuProducts() {
        return menuProducts;
    }

    public void setMenuProducts(MenuProducts menuProducts) {
        this.menuProducts = menuProducts;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuHideResponse menuDto = (MenuHideResponse) o;

        if (displayed != menuDto.displayed) return false;
        if (menuId != null ? !menuId.equals(menuDto.menuId) : menuDto.menuId != null) return false;
        if (name != null ? !name.equals(menuDto.name) : menuDto.name != null) return false;
        if (price != null ? !price.equals(menuDto.price) : menuDto.price != null) return false;
        if (menuGroupId != null ? !menuGroupId.equals(menuDto.menuGroupId) : menuDto.menuGroupId != null) return false;
        return menuProducts != null ? menuProducts.equals(menuDto.menuProducts) : menuDto.menuProducts == null;
    }

    @Override
    public int hashCode() {
        int result = menuId != null ? menuId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (menuGroupId != null ? menuGroupId.hashCode() : 0);
        result = 31 * result + (menuProducts != null ? menuProducts.hashCode() : 0);
        result = 31 * result + (displayed ? 1 : 0);
        return result;
    }
}
