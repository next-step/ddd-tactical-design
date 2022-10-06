package kitchenpos.menus.menu.tobe.domain.exception;

public class InvalidMenuPriceException extends IllegalArgumentException {

    private static final String MESSAGE = "가격 정보는 메뉴상품 금액의 총합보다 적거나 같아야합니다. price=%d, totalAmount=%d";

    public InvalidMenuPriceException(final Long price, final Long totalAmount) {
        super(String.format(MESSAGE, price, totalAmount));
    }
}
