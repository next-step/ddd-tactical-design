package kitchenpos.products.tobe.domain.exception;

public class DisplayedNameContainsProfanityException extends RuntimeException {
    private static final String MESSAGE = "상품명에 비속어가 포함되어 있습니다.";

    public DisplayedNameContainsProfanityException() {
        super(MESSAGE);
    }
}
