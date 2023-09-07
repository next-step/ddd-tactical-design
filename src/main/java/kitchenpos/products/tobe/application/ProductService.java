package kitchenpos.products.tobe.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.tobe.ui.ProductRequest;
import kitchenpos.products.tobe.ui.ProductResponse;
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
    public ProductResponse create(final ProductRequest request) {
        if (purgomalumClient.containsProfanity(request.getName())) {
            throw new IllegalArgumentException();
        }

        Product savedProduct = productRepository.save(Product.of(request));
        return new ProductResponse(savedProduct.getId(), savedProduct.getName(), savedProduct.getPrice());
    }

    @Transactional
    public ProductResponse changePrice(final UUID productId, final ProductRequest request) {
        final Product product = productRepository.findById(productId).orElseThrow(NoSuchElementException::new);

        product.changePrice(request.getPrice());

        menuRepository.findAllByProductId(productId)
                .forEach(Menu::hideIfMenuPriceTooHigher);

        return new ProductResponse(product.getId(), product.getName(), product.getPrice());
    }



    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
