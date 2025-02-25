package kitchenpos.product.domain.service;

import java.util.List;
import kitchenpos.product.domain.model.ProductVo;

public interface ProductQueryService {
    List<ProductVo.ProductInfo> findAll();
}
