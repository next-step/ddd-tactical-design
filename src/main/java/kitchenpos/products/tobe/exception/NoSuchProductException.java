package kitchenpos.products.tobe.exception;

import java.util.NoSuchElementException;

public class NoSuchProductException extends NoSuchElementException {

    public NoSuchProductException() {
        super("상품이 존재하지 않습니다.");
    }

}
