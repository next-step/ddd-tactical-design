package kitchenpos.products.tobe.exception;

public class ProfanityException extends IllegalArgumentException {
    public ProfanityException(String message) {
        super(message);
    }

    public ProfanityException() {
        super("비속어가 포함되어 있습니다.");
    }
}
