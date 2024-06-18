package kitchenpos.menus.tobe.application;

import kitchenpos.menus.tobe.domain.ProductInfo;

import java.util.UUID;

public interface ProductDomainService {
    ProductInfo fetchProductInfo(UUID productId);
}
