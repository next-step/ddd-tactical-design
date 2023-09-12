package kitchenpos.products.tobe.application;

import kitchenpos.menus.tobe.domain.Menu;
import kitchenpos.menus.tobe.domain.MenuRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.infra.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final PurgomalumClient purgomalumClient;

    public ProductService(
        final ProductRepository productRepository,
        final MenuRepository menuRepository,
        final PurgomalumClient purgomalumClient
    ) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public Product create(final ProductRequest request) {
        request.validate(purgomalumClient::containsProfanity);
        final Product product = Product.create(
            UUID.randomUUID(),
            request.getName(),
            request.getPrice(),
            purgomalumClient.containsProfanity(request.getName())
        );
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final ProductRequest request) {
        request.validatePrice();
        final Product product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        product.changePrice(request.getPrice(), menus);
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
