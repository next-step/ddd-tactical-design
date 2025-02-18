package kitchenpos.product.domain.service;

import java.util.List;
import java.util.UUID;
import kitchenpos.product.domain.entity.Product;
import kitchenpos.product.domain.model.ProductVo;

public interface ProductService {
    ProductVo.ProductInfo create(final ProductVo.Create request);
    ProductVo.ProductInfo changePrice(final ProductVo.Update request);
    List<ProductVo.ProductInfo> findAll();
}
