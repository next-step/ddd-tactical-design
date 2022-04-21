package kitchenpos.menus.dto;

import kitchenpos.menus.domain.tobe.domain.vo.MenuId;
import kitchenpos.support.dto.DTO;

import javax.validation.constraints.NotNull;

public class MenuHideRequest extends DTO {
    @NotNull(message = "메뉴 번호 없이는 메뉴 숨김을 요청할 수 없습니다")
    private MenuId menuId;

    public MenuHideRequest() {
    }

    public MenuHideRequest(MenuId menuId) {
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

        MenuHideRequest that = (MenuHideRequest) o;

        return menuId != null ? menuId.equals(that.menuId) : that.menuId == null;
    }

    @Override
    public int hashCode() {
        return menuId != null ? menuId.hashCode() : 0;
    }
}
