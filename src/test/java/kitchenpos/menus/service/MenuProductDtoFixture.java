package kitchenpos.menus.service;

import java.util.UUID;

import kitchenpos.menus.application.dto.MenuProductDto;
import kitchenpos.products.domain.Product;

public class MenuProductDtoFixture {
    private UUID productId;
    private int quantity;

    public MenuProductDtoFixture() {
        productId = UUID.randomUUID();
        quantity = 1;
    }

    public static MenuProductDtoFixture builder() {
        return builder(null);
    }

    public static MenuProductDtoFixture builder(Product product) {
        return new MenuProductDtoFixture()
                .product(product)
                .quantity(1);
    }

    public MenuProductDtoFixture product(Product product) {
        this.productId = null;
        if (product != null) {
            this.productId = product.getId();
        }
        return this;
    }

    public MenuProductDtoFixture quantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public MenuProductDto build() {
        return new MenuProductDto(productId, quantity);
    }
}
