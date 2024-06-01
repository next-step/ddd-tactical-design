package kitchenpos.menus.exception;

import kitchenpos.menus.tobe.domain.menu.MenuPrice;

import java.math.BigDecimal;

public class InvalidMenuPriceDisplayException extends IllegalStateException {
    public InvalidMenuPriceDisplayException(MenuPrice price, BigDecimal menuProductsPrice) {
        super(String.format("메뉴의 가격은 메뉴구성상품의 가격의 총합보다 크면 메뉴가 노출될 수 없다.\n" +
                "=> 메뉴가격[ %s (원)] > 메뉴구성상품들의 가격[ %s (원)] ", price.priceValue(), menuProductsPrice));
    }
}
