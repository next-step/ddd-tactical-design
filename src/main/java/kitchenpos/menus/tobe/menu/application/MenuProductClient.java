package kitchenpos.menus.tobe.menu.application;

import kitchenpos.menus.tobe.menu.ui.dto.ProductResponse;
import kitchenpos.products.tobe.domain.Product;

import java.util.List;
import java.util.UUID;

public interface MenuProductClient {
    List<Product> findAllByIdIn(List<UUID> productIds);
    Product findById(UUID productId);
    List<ProductResponse> findAllByIdn(List<UUID> productIds);
}
