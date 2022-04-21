package kitchenpos.menus.dto;

import kitchenpos.menus.domain.tobe.domain.vo.MenuId;
import kitchenpos.support.dto.DTO;

import javax.validation.constraints.NotNull;

public class MenuDisplayRequest extends DTO {
    @NotNull
    private MenuId menuId;

    public MenuDisplayRequest() {
    }

    public MenuDisplayRequest(MenuId menuId) {
        this.menuId = menuId;
    }

    public MenuId getMenuId() {
        return menuId;
    }

    public void setMenuId(MenuId menuId) {
        this.menuId = menuId;
    }
}
