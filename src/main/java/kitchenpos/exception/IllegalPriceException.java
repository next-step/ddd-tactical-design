package kitchenpos.exception;

public class IllegalPriceException extends IllegalArgumentException {
    public IllegalPriceException(String classType) {
        super(classType + " : 잘못된 가격입니다.");
    }
}
