package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.products.tobe.domain.Price;

public class Menu {


    private final MenuProducts menuProducts;
    private final Price price;

    public Menu(Price price, MenuProducts menuProducts) {
        this.price = price;
        validate(menuProducts);
        this.menuProducts = menuProducts;
    }

    private void validate(MenuProducts menuProducts) {
        validateMenuProductsSize(menuProducts);
        validatePrice(menuProducts);
    }

    private void validatePrice(MenuProducts menuProducts) {
        if (this.price.price().compareTo(menuProducts.sum()) > 0) {
            throw new IllegalArgumentException("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 작을 수 없습니다.");
        }
    }

    private static void validateMenuProductsSize(MenuProducts menuProducts) {
        if (menuProducts.size() < 1) {
            throw new IllegalArgumentException("메뉴 상품을 등록해주세요.");
        }
    }
}
