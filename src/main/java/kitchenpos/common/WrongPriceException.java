package kitchenpos.common;

public class WrongPriceException extends RuntimeException {
    public WrongPriceException(String message){
        super(message);
    }
}
