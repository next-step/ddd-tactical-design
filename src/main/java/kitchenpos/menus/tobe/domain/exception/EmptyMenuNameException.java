package kitchenpos.menus.tobe.domain.exception;

public class EmptyMenuNameException extends IllegalArgumentException {

    public EmptyMenuNameException() {
        super("메뉴 이름은 공백을 가질 수 없다.");
    }
}
