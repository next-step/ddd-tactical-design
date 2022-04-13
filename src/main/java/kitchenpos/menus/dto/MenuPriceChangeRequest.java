package kitchenpos.menus.dto;

import kitchenpos.menus.domain.tobe.domain.vo.MenuId;
import kitchenpos.support.dto.DTO;

import java.math.BigDecimal;

public class MenuPriceChangeRequest extends DTO {
    private MenuId menuId;
    private BigDecimal price;

    public MenuPriceChangeRequest() {
    }

    public MenuPriceChangeRequest(MenuId menuId, BigDecimal price) {
        this.menuId = menuId;
        this.price = price;
    }

    public MenuId getMenuId() {
        return menuId;
    }

    public void setMenuId(MenuId menuId) {
        this.menuId = menuId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
