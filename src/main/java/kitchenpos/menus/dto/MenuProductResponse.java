package kitchenpos.menus.dto;

import kitchenpos.menus.tobe.domain.MenuProduct;

import java.util.UUID;

public record MenuProductResponse(
        Long seq,
        UUID productId,
        long quantity
) {
    public static MenuProductResponse of(MenuProduct menuProduct) {
        return new MenuProductResponse(
                menuProduct.getSeq(),
                menuProduct.getProductId(),
                menuProduct.getQuantity()
        );
    }
}
