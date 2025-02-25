package kitchenpos.product.domain.service;

import kitchenpos.product.domain.model.ProductVo;

public interface ProductCommandService {
    ProductVo.ProductInfo create(final ProductVo.Create request);
    ProductVo.ProductInfo changePrice(final ProductVo.Update request);
}
