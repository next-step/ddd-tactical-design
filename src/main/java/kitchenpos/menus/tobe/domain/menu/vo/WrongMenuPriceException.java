package kitchenpos.menus.tobe.domain.menu.vo;

public class WrongMenuPriceException extends RuntimeException {
    public WrongMenuPriceException() {
    }

    public WrongMenuPriceException(String message) {
        super(message);
    }
}
