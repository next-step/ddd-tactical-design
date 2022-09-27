package kitchenpos.menus.tobe.domain.exception;

public class NullMenuPriceException extends IllegalArgumentException {

    public NullMenuPriceException() {
        super("등록할 메뉴 가격이 없다.");
    }
}
