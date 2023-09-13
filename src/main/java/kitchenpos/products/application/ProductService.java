package kitchenpos.products.application;

import kitchenpos.menus.application.MenuService;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.infra.PurgomalumClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductName;
import kitchenpos.products.tobe.domain.ProductPrice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductService {

    private final MenuService menuService;
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;
    private final PurgomalumClient purgomalumClient;

    public ProductService(
        final MenuService menuService,
        final ProductRepository productRepository,
        final MenuRepository menuRepository,
        final PurgomalumClient purgomalumClient
    ) {
        this.menuService = menuService;
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
        this.purgomalumClient = purgomalumClient;
    }

    @Transactional
    public Product create(final Product request) {
        return productRepository.save(
            Product.of(new ProductName(request.getName(), purgomalumClient),
                new ProductPrice(request.getPrice())));
    }

    @Transactional
    public Product changePrice(final UUID productId, final Product request) {
        final BigDecimal price = request.getPrice();
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        final Product product = productRepository.findById(productId)
            .orElseThrow(NoSuchElementException::new);
        product.changePrice(price);

        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
