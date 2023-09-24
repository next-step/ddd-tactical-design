package kitchenpos.menus.application.loader;

import kitchenpos.common.domain.Price;

import java.util.UUID;

public interface ProductPriceLoader {
    Price findPriceById(UUID productId);
}
