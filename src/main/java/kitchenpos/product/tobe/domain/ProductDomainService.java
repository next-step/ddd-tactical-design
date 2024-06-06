package kitchenpos.product.tobe.domain;

import java.math.BigDecimal;
import java.util.UUID;

public interface ProductDomainService {
    Product syncMenuDisplayStatusWithProductPrices(UUID productId, BigDecimal newPrice);
}
