package kitchenpos.menus.dto;

import kitchenpos.menus.domain.tobe.domain.vo.MenuId;

public class MenuDisplayedChangeRequest {
    private MenuId menuId;

    public MenuDisplayedChangeRequest() {
    }

    public MenuDisplayedChangeRequest(MenuId menuId) {
        this.menuId = menuId;
    }

    public MenuId getMenuId() {
        return menuId;
    }

    public void setMenuId(MenuId menuId) {
        this.menuId = menuId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuDisplayedChangeRequest that = (MenuDisplayedChangeRequest) o;

        return menuId != null ? menuId.equals(that.menuId) : that.menuId == null;
    }

    @Override
    public int hashCode() {
        return menuId != null ? menuId.hashCode() : 0;
    }
}
