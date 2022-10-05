package kitchenpos.common.domain.vo.exception;

public class InvalidDisplayedNameException extends IllegalArgumentException {
    private static final String MESSAGE = "이름은 비어있거나, 비속어가 포함될 수 없습니다.";

    public InvalidDisplayedNameException() {
        super(MESSAGE);
    }
}
