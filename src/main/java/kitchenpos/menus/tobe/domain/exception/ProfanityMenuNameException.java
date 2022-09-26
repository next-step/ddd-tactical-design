package kitchenpos.menus.tobe.domain.exception;

public class ProfanityMenuNameException extends IllegalArgumentException {

    public ProfanityMenuNameException() {
        super("욕설이 포함된 메뉴 이름은 등록할 수 없다.");
    }
}
