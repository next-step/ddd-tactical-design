package kitchenpos.menus.application;

import kitchenpos.common.domain.Price;

import java.util.UUID;

public interface ProductPriceLoader {
    Price findPriceById(UUID productId);
}
