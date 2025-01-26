package kitchenpos.menus.tobe.domain;

public class NotRegistrableMenuPriceException extends IllegalArgumentException {
    public NotRegistrableMenuPriceException() {
        super("등록할 수 없는 가격정보입니다.");
    }
}
    