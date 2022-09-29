package kitchenpos.global.exception;

public class ProfanityNameException extends IllegalArgumentException {

    public ProfanityNameException() {
        super("욕설이 포함된 이름은 등록할 수 없다.");
    }
}
