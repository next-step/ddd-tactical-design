package kitchenpos.menus.ui.dto;

import kitchenpos.common.vo.Price;
import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuId;

public class MenuChangePriceResponse {

    private MenuId id;

    private Price price;

    public MenuChangePriceResponse(MenuId id, Price price) {
        this.id = id;
        this.price = price;
    }

    public static MenuChangePriceResponse from(Menu menu) {
        return new MenuChangePriceResponse(menu.getId(), menu.getPrice());
    }

    public MenuId getId() {
        return id;
    }

    public Price getPrice() {
        return price;
    }
}
