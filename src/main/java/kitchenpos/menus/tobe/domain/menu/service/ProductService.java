package kitchenpos.menus.tobe.domain.menu.service;

import kitchenpos.menus.tobe.domain.menu.ProductPriceResponse;

import java.util.List;

public interface ProductService {
    List<ProductPriceResponse> findAllPrices(List<Long> ids);
}
