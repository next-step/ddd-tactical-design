package kitchenpos.menus.menugroup.tobe.domain.exception;

public class InvalidMenuGroupNameException extends IllegalArgumentException {

    private static final String MESSAGE = "메뉴그룹명은 비어있을 수 없습니다.";

    public InvalidMenuGroupNameException() {
        super(MESSAGE);
    }
}
