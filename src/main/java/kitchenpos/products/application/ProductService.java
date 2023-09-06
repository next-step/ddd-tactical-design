package kitchenpos.products.application;

import kitchenpos.menus.application.MenuService;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.dto.ProductChangePriceRequest;
import kitchenpos.products.dto.ProductResponse;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPrice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final MenuService menuService;
    private final PurgomalumClient purgomalumClient;

    public ProductService(
            final ProductRepository productRepository,
            final MenuService menuService,
            final PurgomalumClient purgomalumClient
    ) {
        this.productRepository = productRepository;
        this.menuService = menuService;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public Product create(final ProductResponse request) {
        Product product = request.toEntity(purgomalumClient);
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final ProductChangePriceRequest request) {
        Product product = findById(productId);
        ProductPrice changePrice = new ProductPrice(request.getPrice());
        product.changePrice(changePrice);

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
