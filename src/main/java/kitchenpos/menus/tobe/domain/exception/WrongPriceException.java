package kitchenpos.menus.tobe.domain.exception;

public class WrongPriceException extends IllegalArgumentException {
    public static final String PRICE_SHOULD_NOT_BE_NEGATIVE = "price는 0보다 작을 수 없습니다.";
    public static final String PRICE_SHOULD_NOT_BE_MORE_THAN_TOTAL_PRODUCTS_PRICE = "메뉴의 가격은 메뉴에 속한 상품 금액의 합보다 같거나 작아야합니다.";

    public WrongPriceException() {
        super(PRICE_SHOULD_NOT_BE_NEGATIVE);
    }

    public WrongPriceException(final String message) {
        super(message);
    }
}
