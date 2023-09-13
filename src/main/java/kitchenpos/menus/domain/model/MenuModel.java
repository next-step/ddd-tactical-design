package kitchenpos.menus.domain.model;

import kitchenpos.menus.domain.MenuGroup;
import kitchenpos.menus.domain.vo.MenuName;
import kitchenpos.menus.domain.vo.MenuPrice;
import kitchenpos.menus.domain.vo.MenuProducts;
import kitchenpos.support.ValueObject;

public class MenuModel extends ValueObject {

    private MenuName menuName;
    private MenuPrice price;
    private MenuGroup menuGroup;
    private boolean displayed;
    private MenuProducts menuProducts;

    public MenuModel(MenuName menuName, MenuPrice price, MenuGroup menuGroup, boolean displayed, MenuProducts menuProducts) {
        this.menuName = menuName;
        this.price = price;
        this.menuGroup = menuGroup;
        this.displayed = displayed;
        this.menuProducts = menuProducts;
    }
}
