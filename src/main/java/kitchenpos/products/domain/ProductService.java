package kitchenpos.products.domain;

import java.util.List;
import java.util.UUID;
import kitchenpos.products.domain.dots.ProductCommand;
import kitchenpos.products.domain.dots.ProductInfo;

public interface ProductService {

    ProductInfo create(final ProductCommand.RegisterProductCommand command);

    ProductInfo changePrice(final UUID productId, final ProductCommand.ChangePriceCommand command);

    List<ProductInfo> findAll();
}
