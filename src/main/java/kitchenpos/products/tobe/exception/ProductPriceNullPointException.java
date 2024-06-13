package kitchenpos.products.tobe.exception;

public class ProductPriceNullPointException extends IllegalArgumentException {

    public ProductPriceNullPointException() {
        super("상품의 가격은 빈 값일 수 없습니다.");
    }

}
