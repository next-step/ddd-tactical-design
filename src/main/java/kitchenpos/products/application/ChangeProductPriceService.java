package kitchenpos.products.application;

import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import kitchenpos.products.tobe.domain.service.ProductMenuPricePolicy;
import kitchenpos.products.tobe.domain.vo.ProductPrice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ChangeProductPriceService {
    private final MenuRepository menuRepository;

    private final ProductRepository productRepository;

    public ChangeProductPriceService(
        final MenuRepository menuRepository,
        final ProductRepository productRepository
    ) {
        this.menuRepository = menuRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public Product changePrice(final UUID productId, final BigDecimal price) {
        final ProductPrice productPrice = new ProductPrice(price);
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);

        product.changePrice(productPrice, new ProductMenuPricePolicy(menuRepository));

        return product;
    }
}
