package kitchenpos.menu.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.product.domain.entity.Product;

public interface ProductContextProvider {
    List<Product> findAllByIds(List<UUID> productIds);
    BigDecimal getTotalPrice(UUID productId, BigDecimal qty);
}
