package kitchenpos.products.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.*;
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
        Product product = Product.of(request);
        if (purgomalumClient.containsProfanity(product.getName())) {
            throw new IllegalArgumentException();
        }
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final ProductChangeRequest request) {
        ProductPolicy.checkPrice(request.getPrice());

        Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        product.setPrice(request.getPrice());
        productRepository.save(product);

        List<Menu> menus = menuRepository.findAllByProductId(productId);
        MenuPolicy.applyDisplayedPolicy(menus);

        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
