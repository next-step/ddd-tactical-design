package kitchenpos.common.tobe.exception;

public class WrongDisplayedNameException extends IllegalArgumentException {
    public static final String DISPLAYED_NAME_SHOULD_NOT_BE_EMPTY = "DisplayedName은 비워 둘 수 없습니다.";
    public static final String DISPLAYED_NAME_SHOULD_NOT_CONTAIN_PROFANITY = "DisplayedName은 비속어가 포함될 수 없습니다.";

    public WrongDisplayedNameException(final String message) {
        super(message);
    }
}
