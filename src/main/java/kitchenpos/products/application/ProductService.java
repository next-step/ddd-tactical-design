package kitchenpos.products.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.dto.ProductChangePriceRequest;
import kitchenpos.products.dto.ProductCreateRequest;
import kitchenpos.products.infra.PurgomalumClient;
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
    public Product create(final ProductCreateRequest request) {
        final Product product = request.toEntity();
        checkContainsProfanity(product);
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final ProductChangePriceRequest request) {
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        final Product product = findById(productId);

        product.changePrice(request.getPrice());
        menus.forEach(Menu::checkDisplay);
        return product;
    }

    private Product findById(final UUID productId) {
        return productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);
    }


    // TODO: 리펙토링
    private void checkContainsProfanity(final Product product) {
        if (purgomalumClient.containsProfanity(product.getName())) {
            throw new IllegalArgumentException();
        }
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
