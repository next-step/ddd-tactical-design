package kitchenpos.products.tobe.application;

import kitchenpos.menus.tobe.domain.application.HideMenuWithInvalidPriceByProductId;
import kitchenpos.products.tobe.domain.application.ChangePrice;
import kitchenpos.products.tobe.domain.application.CreateProduct;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.dto.ProductCreateDto;
import kitchenpos.products.tobe.dto.ProductPriceChangeDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProductCommandHandler {
    private final CreateProduct createProduct;
    private final ChangePrice changePrice;
    private final HideMenuWithInvalidPriceByProductId hideMenuWithInvalidPriceByProductId;

    public ProductCommandHandler(CreateProduct createProduct, ChangePrice changePrice, HideMenuWithInvalidPriceByProductId hideMenuWithInvalidPriceByProductId) {
        this.createProduct = createProduct;
        this.changePrice = changePrice;
        this.hideMenuWithInvalidPriceByProductId = hideMenuWithInvalidPriceByProductId;
    }

    @Transactional
    public Product create(final ProductCreateDto request) {
        return createProduct.execute(request);
    }

    // TODO: 동시성 제어
    @Transactional
    public Product changePrice(final UUID productId, final ProductPriceChangeDto request) {
        Product product = changePrice.execute(productId, request);
        hideMenuWithInvalidPriceByProductId.execute(productId);
        return product;
    }
}
