package kitchenpos.products.tobe.application;

import kitchenpos.menus.tobe.application.ProductPriceService;
import kitchenpos.products.tobe.ProductRepository;
import kitchenpos.products.tobe.ProductPrices;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultProductPriceService implements ProductPriceService {

    private final ProductRepository productRepository;

    public DefaultProductPriceService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductPrices getProductPrices(List<UUID> productIds) {
        return ProductPrices.from(productRepository.findAllByIdIn(productIds));
    }
}
