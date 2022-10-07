package kitchenpos.common.domain.vo.exception;

public class InvalidNameException extends IllegalArgumentException {

    private static final String MESSAGE = "이름은 비어있을 수 없습니다.";

    public InvalidNameException() {
        super(MESSAGE);
    }
}
