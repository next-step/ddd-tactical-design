package kitchenpos.menus.service;

import java.util.UUID;

import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.Price;
import kitchenpos.menus.domain.Quantity;
import kitchenpos.products.domain.Product;

public class MenuProductFixture {
    private UUID productId;
    private int quantity;
    private long price;

    public MenuProductFixture() {
        productId = null;
        quantity = 1;
        price = 0L;
    }

    public static MenuProductFixture builder() {
        return builder(null);
    }

    public static MenuProductFixture builder(Product product) {
        return new MenuProductFixture()
                .product(product)
                .quantity(1);
    }

    public MenuProductFixture product(Product product) {
        this.productId = null;
        if (product != null) {
            this.productId = product.getId();
            this.price = product.getPrice().longValue();
        }
        return this;
    }

    public MenuProductFixture quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public MenuProductFixture price(long price) {
        this.price = price;
        return this;
    }

    public MenuProduct build() {
        return new MenuProduct(productId, new Quantity(quantity), new Price(price));
    }
}
