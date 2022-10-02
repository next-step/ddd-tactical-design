package kitchenpos.menus.menu.tobe.domain.exception;

public class InvalidMenuException extends IllegalArgumentException {

    public static final String MENU_NAME_MESSAGE = "메뉴명 정보가 있어야 합니다.";
    public static final String PRICE_MESSAGE = "가격정보가 있어야 합니다.";
    public static final String MENU_GROUP_ID_MESSAGE = "메뉴그룹 정보가 있어야 합니다.";
    public static final String MENU_PRODUCTS_MESSAGE = "메뉴상품 목록 정보가 있어야 합니다.";
    private static final String INVALID_PRICE_MESSAGE = "가격 정보는 메뉴상품 금액의 총합보다 적거나 같아야합니다. price=%d, totalAmount=%d";

    public InvalidMenuException(final String message) {
        super(message);
    }

    public InvalidMenuException(final Long price, final Long totalAmount) {
        super(String.format(INVALID_PRICE_MESSAGE, price, totalAmount));
    }
}
