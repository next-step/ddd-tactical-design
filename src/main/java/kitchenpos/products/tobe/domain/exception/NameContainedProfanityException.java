package kitchenpos.products.tobe.domain.exception;

public class NameContainedProfanityException extends RuntimeException {

    private static final String MESSAGE = "이름에 욕설이 있을 수 없습니다.";

    public NameContainedProfanityException() {
        super(MESSAGE);
    }
}
