package kitchenpos.menus.domain.tobe;

import java.util.UUID;

public class Menu {
    private final UUID id;
    private final DisplayedName name;
    private final int price;
    private final MenuProducts products;
    private boolean displayed = true;

    public Menu(UUID id, DisplayedName name, int price, MenuProducts products) {
        if (products.totalAmount() < price) {
            throw new IllegalArgumentException("메뉴의 가격은 메뉴 상품들의 합보다 적거나 같아야 합니다.");
        }

        this.id = id;
        this.name = name;
        this.price = price;
        this.products = products;
    }

    public void changePrice(final int price) {
        if (products.totalAmount() < price) {
            this.displayed = false;
        }
    }
}
