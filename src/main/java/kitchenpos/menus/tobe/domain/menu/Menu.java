package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.menus.domain.MenuProduct;

import java.util.List;

public class Menu {


    private final List<MenuProduct> menuProducts;

    public Menu(List<MenuProduct> menuProducts) {
        validate(menuProducts);
        this.menuProducts = menuProducts;
    }

    private void validate(List<MenuProduct> menuProducts) {
        if (menuProducts.size() < 1) {
            throw new IllegalArgumentException("메뉴 상품을 등록해주세요.");
        }
    }
}
