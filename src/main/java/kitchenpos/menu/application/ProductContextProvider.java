package kitchenpos.menu.application;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.menu.domain.model.MenuProductQty;
import kitchenpos.product.domain.entity.Product;
import kitchenpos.product.domain.model.ProductId;

public interface ProductContextProvider {
    List<Product> findAllByProductIds(List<ProductId> productIds);
    BigDecimal getTotalPrice(ProductId productId, MenuProductQty qty);
    List<ProductId> validateProduct(List<ProductId> productIds, int menuProductsSize);
}
