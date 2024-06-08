package kitchenpos.products.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;

public interface ProductPriceService {
    Product syncMenuDisplayStatusWithProductPrices(UUID productId, BigDecimal newPrice);
}
