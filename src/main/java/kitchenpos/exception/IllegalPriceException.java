package kitchenpos.exception;

public class IllegalPriceException extends IllegalArgumentException {
    public IllegalPriceException(String descriptions, String p) {
        super(descriptions+ " " + p);
    }

    public IllegalPriceException(String descriptions, Long price) {
        super(descriptions + " " + price);
    }
}
