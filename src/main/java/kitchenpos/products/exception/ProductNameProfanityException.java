package kitchenpos.products.exception;

public class ProductNameProfanityException extends IllegalArgumentException {
    public ProductNameProfanityException(String value) {
        super("상품 이름에는 비속어가 포함될 수 없다. value : " + value);
    }
}
