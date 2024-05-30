package kitchenpos.menus.exception;

import java.util.NoSuchElementException;
import java.util.UUID;

public class MenuProductsNoSuchElementException extends NoSuchElementException {
    public MenuProductsNoSuchElementException(UUID productId) {
        super("메뉴구성상품 목록에서 해당 메뉴구성상품을 찾을 수 없습니다. productId : " + productId);
    }
}
