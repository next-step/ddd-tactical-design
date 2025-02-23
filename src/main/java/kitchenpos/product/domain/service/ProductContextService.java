package kitchenpos.product.domain.service;

import java.util.List;
import java.util.UUID;
import kitchenpos.product.domain.entity.Product;
import kitchenpos.product.domain.model.ProductVo;

public interface ProductContextService {
    List<Product> findAllByIds(List<UUID> productIds);
}
