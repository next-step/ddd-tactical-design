package kitchenpos.menus.domain.menu;

import kitchenpos.common.domain.Price;

import java.util.List;
import java.util.UUID;

public interface ProductClient {
    Price getProductPrice(final UUID productId);
    boolean isInvalidMenuProductsCount(final List<MenuProduct> menuProducts);
}
