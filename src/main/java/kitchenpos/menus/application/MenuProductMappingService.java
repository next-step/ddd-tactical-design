package kitchenpos.menus.application;

import kitchenpos.products.tobe.domain.Product;

import java.util.UUID;


public interface MenuProductMappingService {
    Product findById(UUID productId);
}
