package kitchenpos.menus.exception;

public class InvalidMenuPriceException extends IllegalArgumentException{

    private static final String EXCEPTION_MESSAGE = "메뉴의 가격은 0원 이상이어야 한다. price = ";

    public InvalidMenuPriceException(Object value) {
        super(EXCEPTION_MESSAGE + value);
    }
}
