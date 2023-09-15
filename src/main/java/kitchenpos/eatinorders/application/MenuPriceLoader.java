package kitchenpos.eatinorders.application;

import kitchenpos.common.domain.Price;

import java.util.UUID;

public interface MenuPriceLoader {
    Price findMenuPriceById(UUID menuId);
}
