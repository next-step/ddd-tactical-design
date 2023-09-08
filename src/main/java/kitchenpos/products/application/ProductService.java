package kitchenpos.products.application;

import kitchenpos.menus.application.MenuService;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.dto.ProductRequest;
import kitchenpos.profanity.ProfanityClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPrice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final MenuService menuService;
    private final ProfanityClient profanityClient;

    public ProductService(
            final ProductRepository productRepository,
            final MenuService menuService,
            final ProfanityClient profanityClient
    ) {
        this.productRepository = productRepository;
        this.menuService = menuService;
        this.profanityClient = profanityClient;
    }

    @Transactional
    public Product create(final ProductRequest request) {
        Product product = request.toEntity(profanityClient);
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, BigDecimal price) {
        Product product = findById(productId);
        ProductPrice productPrice = new ProductPrice(price);
        product.changePrice(productPrice);

        menuService.hideMenuIfMenuPriceGreaterThanProductPrice(productId);
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
    }
}
