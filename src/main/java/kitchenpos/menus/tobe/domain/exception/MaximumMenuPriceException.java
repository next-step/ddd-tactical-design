package kitchenpos.menus.tobe.domain.exception;

public class MaximumMenuPriceException extends IllegalArgumentException {

    public MaximumMenuPriceException() {
        super("메뉴 가격은 제품 금액의 합보다 작거나 같아야 한다.");
    }
}
