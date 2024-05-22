package kitchenpos.products.exception;

public class ProductNameNullPointerException extends IllegalArgumentException {
    public ProductNameNullPointerException() {
        super("상품 이름은 비워둘 수 없다.");
    }
}
