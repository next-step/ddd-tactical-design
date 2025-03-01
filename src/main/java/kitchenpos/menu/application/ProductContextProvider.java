package kitchenpos.menu.application;

import java.math.BigDecimal;
import java.util.List;
import kitchenpos.menu.domain.model.MenuProductQty;
import kitchenpos.product.domain.entity.Product;
import kitchenpos.product.domain.model.ProductId;

public interface ProductContextProvider {
    BigDecimal getTotalPrice(ProductId productId, MenuProductQty qty);
    void validateProduct(List<ProductId> productIds, int menuProductsSize);
}
