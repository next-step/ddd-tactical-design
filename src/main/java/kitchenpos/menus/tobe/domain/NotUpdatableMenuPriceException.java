package kitchenpos.menus.tobe.domain;

public class NotUpdatableMenuPriceException extends IllegalArgumentException {
    public NotUpdatableMenuPriceException() {
        super("수정할 수 없는 가격정보입니다.");
    }
}
