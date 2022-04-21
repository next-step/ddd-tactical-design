package kitchenpos.menus.dto;

import kitchenpos.menus.domain.tobe.domain.vo.MenuId;
import kitchenpos.support.dto.DTO;

import javax.validation.constraints.NotNull;

public class MenuDisplayRequest extends DTO {
    @NotNull(message = "메뉴 번호 없이는 메뉴 전시를 요청할 수 없습니다")
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
