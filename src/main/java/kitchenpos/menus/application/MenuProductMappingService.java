package kitchenpos.menus.application;

import kitchenpos.common.domain.Price;

import java.util.UUID;


public interface MenuProductMappingService {
    Price findPriceById(UUID productId);
}
