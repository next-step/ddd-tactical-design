package kitchenpos.menus.menu.tobe.domain.vo.exception;

public class InvalidMenuNameException extends IllegalArgumentException {
    private static final String MESSAGE = "메뉴명은 비어있거나, 비속어가 포함될 수 없습니다.";

    public InvalidMenuNameException() {
        super(MESSAGE);
    }
}
