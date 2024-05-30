package kitchenpos.menus.exception;

public class InvalidMenuProductsException extends IllegalArgumentException {
    public InvalidMenuProductsException() {
        super("유효하지 않은 메뉴구성상품 목록입니다. 이미 생성된 상품중에서 메뉴구성상품을 1개 이상 구성하세요.");
    }
}
