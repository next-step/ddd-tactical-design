package kitchenpos.menu.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;

public interface MenuService {
    void syncMenuDisplayStatisWithProductPrices(UUID productId, BigDecimal newPrice);
}
