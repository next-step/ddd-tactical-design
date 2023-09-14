package kitchenpos.menus.infra;

import kitchenpos.common.domain.Price;
import kitchenpos.menus.application.MenuProductMappingService;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class DefaultMenuProductMappingService implements MenuProductMappingService {

    private final ProductRepository productRepository;

    public DefaultMenuProductMappingService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Price findPriceById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new)
                .getPrice();
    }
}
