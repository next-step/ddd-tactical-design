package kitchenpos.products.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.dto.ProductDto;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.ProductPrice;
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

    public Product create(final ProductDto request) {
        checkPurifiedName(request.getName());
        return saveProduct(request);
    }

    @Transactional
    Product saveProduct(ProductDto request) {
        final Product product = new Product(
                UUID.randomUUID(),
                request.getName(),
                request.getPrice()
        );
        return productRepository.save(product);
    }

    private void checkPurifiedName(String name) {
        if (purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
    }

    @Transactional
    public Product changePrice(final UUID productId, final ProductDto request) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        final List<Menu> menus = menuRepository.findAllByProductId(productId);

        product.changePrice(request.getPrice());

        menus.stream()
                .filter(menu -> menu.isOverPrice())
                .forEach(menu -> menu.close());

        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
