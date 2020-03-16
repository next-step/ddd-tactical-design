package kitchenpos.products.tobe.exception;

public class WrongProductPriceException extends RuntimeException {
    public WrongProductPriceException(String message){
        super(message);
    }
}
