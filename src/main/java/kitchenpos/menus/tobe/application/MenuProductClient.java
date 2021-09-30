package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.ProductVO;
import kitchenpos.products.tobe.domain.Product;

import java.util.List;
import java.util.UUID;

public interface MenuProductClient {
    List<Product> findAllByIdIn(List<UUID> productIds);
    Product findById(UUID productId);
    List<ProductVO> findAllByIdn(List<UUID> productIds);
}
