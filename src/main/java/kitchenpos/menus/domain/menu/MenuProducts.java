package kitchenpos.menus.domain.menu;

import java.util.List;

public class MenuProducts {
    private final List<MenuProduct> menuProducts;

    private MenuProducts(final List<MenuProduct> menuProducts) {
        validateNullOrEmpty(menuProducts);
        validateQuantity(menuProducts);
        this.menuProducts = menuProducts;
    }

    public static MenuProducts of(final List<MenuProduct> menuProducts) {
        return new MenuProducts(menuProducts);
    }

    private static void validateNullOrEmpty(final List<MenuProduct> menuProducts) {
        if (menuProducts == null || menuProducts.isEmpty()) {
            throw new IllegalArgumentException("메뉴의 상품정보가 비어 있습니다.");
        }
    }

    private static void validateQuantity(final List<MenuProduct> menuProducts) {
        // 메뉴 상품들중에 수량이 0보다 작은게 있는지 확인
        menuProducts.stream()
                .map(MenuProduct::getQuantity)
                .filter(quantity -> quantity < 0)
                .findAny()
                .ifPresent(quantity -> {
                    throw new IllegalArgumentException("메뉴 상품의 수량은 0보다 작을 수 없습니다.");
                });
    }

    public List<MenuProduct> getMenuProducts() {
        return menuProducts;
    }
}
