package kitchenpos.menus.tobe.domain.menu;

public class MenuPrice {

    private final int price;

    public MenuPrice(int price) {
        validate(price);
        this.price = price;
    }

    private void validate(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("메뉴 가격은 0원 이하일 수 없습니다.");
        }
    }
}
