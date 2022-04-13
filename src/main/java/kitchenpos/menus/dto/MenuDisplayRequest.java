package kitchenpos.menus.dto;

import kitchenpos.support.dto.DTO;
import kitchenpos.support.policy.PricingRule;
import kitchenpos.menus.domain.tobe.domain.vo.MenuId;

public class MenuDisplayRequest extends DTO {
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
