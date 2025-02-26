package kitchenpos.menu.application;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.product.domain.entity.Product;
import kitchenpos.product.domain.model.ProductId;

public interface ProductContextProvider {
    List<Product> findAllByProductIds(List<ProductId> productIds);
    BigDecimal getTotalPrice(ProductId productId, BigDecimal qty);
}
