package kitchenpos.menus.tobe.domain.model;

import kitchenpos.global.domain.vo.Price;
import kitchenpos.menus.tobe.domain.dto.ProductResponse;
import kitchenpos.menus.tobe.domain.exception.IllegalMenuProductQuantityException;

import java.util.UUID;

public final class MenuProduct {

    private static final int MIN_MENU_PRODUCT_QUANTITY = 1;

    private Long id;
    private Price price;
    private UUID productId;
    private long quantity;

    public MenuProduct(ProductResponse product, long quantity) {
        validate(quantity);
        this.price = product.getPrice();
        this.quantity = quantity;
        this.productId = product.getId();
    }

    private void validate(long quantity) {
        if(quantity < MIN_MENU_PRODUCT_QUANTITY) {
            throw new IllegalMenuProductQuantityException();
        }
    }

    public Price getSubTotalPrice() {
        return this.price.multiply(this.quantity);
    }
}
