package kitchenpos.menus.tobe.domain.menu.vo;

public class WrongInputProductException extends RuntimeException {
    public WrongInputProductException(String message) {
        super(message);
    }
}
