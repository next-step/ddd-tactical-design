package kitchenpos.menus.tobe.domain.exception;

public class InvalidMenuProductPricePeriodException extends RuntimeException {
    private static final String MESSAGE = "메뉴 상품의 가격은 0원 이상이어야 합니다.";

    public InvalidMenuProductPricePeriodException() {
        super(MESSAGE);
    }
}
