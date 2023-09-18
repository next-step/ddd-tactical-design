package kitchenpos.menus.tobe.domain.menu;

import kitchenpos.products.tobe.domain.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ProductClient {
    BigDecimal getProductPrice(UUID productId);

    void validProductIds(List<UUID> productIds);
}
