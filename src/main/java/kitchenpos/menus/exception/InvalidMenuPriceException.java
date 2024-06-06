package kitchenpos.menus.exception;

import kitchenpos.support.domain.MenuPrice;

import java.math.BigDecimal;

public class InvalidMenuPriceException extends IllegalArgumentException{

    private static final String EXCEPTION_MESSAGE = "메뉴의 가격은 0원 이상이어야 한다. price = ";

    public InvalidMenuPriceException(Object value) {
        super(EXCEPTION_MESSAGE + value);
    }

    public InvalidMenuPriceException(MenuPrice price, BigDecimal menuProductsPrice) {
        super(String.format("메뉴의 가격은 메뉴구성상품의 가격의 총합보다 크면 안된다. \n" +
                        "=> 메뉴가격[ %s (원)] > 메뉴구성상품들의 가격[ %s (원)] ", price.priceValue(), menuProductsPrice));
    }
}
