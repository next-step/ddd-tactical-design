package kitchenpos.products.exception;

public class InvalidProductPriceException extends IllegalArgumentException{

    private static final String EXCEPTION_MESSAGE = "상품의 가격은 0원 이상이어야 한다. price = ";

    public InvalidProductPriceException(Object value) {
        super(EXCEPTION_MESSAGE + value);
    }
}
