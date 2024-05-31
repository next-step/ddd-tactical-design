package kitchenpos.menus.dto;

import kitchenpos.menus.tobe.domain.menu.MenuProduct;

import java.util.UUID;

public record MenuProductResponse(
        Long seq,
        UUID productId,
        long quantity
) {
    public static MenuProductResponse from(MenuProduct menuProduct) {
        return new MenuProductResponse(
                menuProduct.getSeq(),
                menuProduct.getProductId(),
                menuProduct.getQuantity()
        );
    }
}
