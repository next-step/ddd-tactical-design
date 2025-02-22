package kitchenpos.menus.tobe.exception;

public class InvalidMenuPriceException extends RuntimeException {

    private static final String MESSAGE = "메뉴의 가격은 모든 메뉴 상품 금액의 총합보다 작아야 합니다.";

    public InvalidMenuPriceException() {
        super(MESSAGE);
    }
}
