package kitchenpos.menus.tobe.domain.exception;

public class IllegalMenuProductSizeException extends IllegalArgumentException{

    private static final String DEFAULT_MESSAGE = "반드시 하나 이상의 상품을 가져야 합니다.";

    public IllegalMenuProductSizeException() {
        super(DEFAULT_MESSAGE);
    }

    public IllegalMenuProductSizeException(String message) {
        super(message);
    }
}
