package kitchenpos.menus.tobe.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ProductClient {
    BigDecimal productPrice(UUID productId);

    int countMatchingProductIdIn(List<UUID> productIds);
}
