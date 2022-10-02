package kitchenpos.menus.menu.tobe.domain.exception;

public class InvalidMenuProductException extends IllegalArgumentException {

    public static final String PRODUCT_ID_MESSAGE = "제품정보가 있어야 합니다.";
    public static final String PRICE_MESSAGE = "가격정보가 있어야 합니다.";
    public static final String QUANTITY_MESSAGE = "수량정보가 있어야 합니다.";

    public InvalidMenuProductException(final String message) {
        super(message);
    }
}
