package kitchenpos.global.exception;

public class NonInstantiableException extends UnsupportedOperationException {

    public NonInstantiableException() {
        super("This class cannot be instantiated.");
    }
}
