package kitchenpos.product.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;

public interface ProductService {
    void syncMenuDisplayStatisWithProductPrices(UUID productId, BigDecimal newPrice);
}
