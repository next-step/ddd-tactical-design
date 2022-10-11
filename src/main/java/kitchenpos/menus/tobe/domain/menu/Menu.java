package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.products.tobe.domain.Price;

import javax.persistence.Column;
import java.math.BigDecimal;

public class Menu {


    private final MenuProducts menuProducts;
    private final Price price;

    @Column(name = "displayed", nullable = false)
    private boolean displayed;

    public Menu(Price price, MenuProducts menuProducts) {
        this.price = price;
        validate(price, menuProducts);
        this.menuProducts = menuProducts;
    }

    private void validate(Price price, MenuProducts menuProducts) {
        validateMenuProductsSize(menuProducts);
        validatePrice(price, menuProducts);
    }

    private void validatePrice(Price price, MenuProducts menuProducts) {
        if (this.price.price().compareTo(menuProducts.sum()) > 0) {
            throw new IllegalArgumentException("메뉴에 속한 상품 금액의 합은 메뉴의 가격보다 작을 수 없습니다.");
        }
        if (price.price().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("메뉴의 가격은 0원 이상이어야 한다.");
        }
    }

    private static void validateMenuProductsSize(MenuProducts menuProducts) {
        if (menuProducts.size() < 1) {
            throw new IllegalArgumentException("메뉴 상품을 등록해주세요.");
        }
    }

    public void hide() {
        this.displayed = false;
    }

    public boolean isDisplayed() {
        return displayed;
    }
}
