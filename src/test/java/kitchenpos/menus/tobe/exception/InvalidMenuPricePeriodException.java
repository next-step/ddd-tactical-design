package kitchenpos.menus.tobe.exception;

public class InvalidMenuPricePeriodException extends RuntimeException {
    private static final String MESSAGE = "메뉴 가격은 0원 이상이어야 합니다.";

    public InvalidMenuPricePeriodException() {
        super(MESSAGE);
    }
}
