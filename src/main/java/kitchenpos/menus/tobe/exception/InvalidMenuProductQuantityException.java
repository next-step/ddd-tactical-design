package kitchenpos.menus.tobe.exception;

public class InvalidMenuProductQuantityException extends IllegalArgumentException {
    public InvalidMenuProductQuantityException(long quantity) {
        super("메뉴구성상품의 수량은 0개 이상이어야 합니다. quantity : " + quantity);

    }
}
