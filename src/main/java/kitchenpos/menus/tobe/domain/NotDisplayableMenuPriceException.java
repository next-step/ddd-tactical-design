package kitchenpos.menus.tobe.domain;

public class NotDisplayableMenuPriceException extends IllegalStateException {
    public NotDisplayableMenuPriceException() {
        super("상품 총 가격이 메뉴의 가격보다 높아 전시할 수 없습니다.");
    }
}
