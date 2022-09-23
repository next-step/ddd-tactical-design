package kitchenpos.products.tobe.domain.exception;

public class ProfanityProductNameException extends IllegalArgumentException {

    public ProfanityProductNameException() {
        super("욕설이 포함된 상품 이름은 등록할 수 없다.");
    }
}
