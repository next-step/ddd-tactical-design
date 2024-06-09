package kitchenpos.products.tobe.application;

import java.util.UUID;
import kitchenpos.products.tobe.domain.application.ChangePrice;
import kitchenpos.products.tobe.domain.application.CreateProduct;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.dto.ProductCreateDto;
import kitchenpos.products.tobe.dto.ProductPriceChangeDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductCommandHandler {
    private final CreateProduct createProduct;
    private final ChangePrice changePrice;

    public ProductCommandHandler(CreateProduct createProduct, ChangePrice changePrice) {
        this.createProduct = createProduct;
        this.changePrice = changePrice;
    }

    @Transactional
    public Product create(final ProductCreateDto request) {
        return createProduct.execute(request);
    }

    @Transactional
    public Product changePrice(final UUID productId, final ProductPriceChangeDto request) {
        Product product = changePrice.execute(productId, request);
        return product;
    }
}
