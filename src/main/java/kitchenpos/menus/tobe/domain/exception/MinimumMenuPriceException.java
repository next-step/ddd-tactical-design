package kitchenpos.menus.tobe.domain.exception;

public class MinimumMenuPriceException extends IllegalArgumentException {

    public MinimumMenuPriceException() {
        super("메뉴 가격은 0원 이상이어야 한다.");
    }
}
