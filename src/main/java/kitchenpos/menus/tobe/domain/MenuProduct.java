package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;

public class MenuProduct {
    private final int MIN_QUANTITY = 0;
    private final String INVALID_QUANTITY_MESSAGE = "잘못된 메뉴 상품의 수량 입니다.";

    private final Product product;
    private final int quantity;

    public MenuProduct(Product product, int quantity) {
        validateQuantity(quantity);
        this.quantity = quantity;
        this.product = product;
    }

    private void validateQuantity(int quantity) {
        if (quantity < MIN_QUANTITY) {
            throw new IllegalArgumentException(INVALID_QUANTITY_MESSAGE);
        }
    }

    public BigDecimal totalPrice() {
        return product.multiplyPrice(BigDecimal.valueOf(quantity));
    }
}
