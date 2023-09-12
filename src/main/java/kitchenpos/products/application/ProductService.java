package kitchenpos.products.application;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuProduct;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductDomainService;
import kitchenpos.products.tobe.domain.ProductRepository;
import kitchenpos.products.infra.PurgomalumClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;
    private final ProductDomainService productDomainService;

    public ProductService(
        final ProductRepository productRepository,
        final PurgomalumClient purgomalumClient,
        final ProductDomainService productDomainService
    ) {
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
        this.productDomainService = productDomainService;
    }

    @Transactional
    public Product create(final Product request) {
        final BigDecimal price = request.getPrice();
        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }
        final String name = request.getName();
        if (Objects.isNull(name) || purgomalumClient.containsProfanity(name)) {
            throw new IllegalArgumentException();
        }
        final Product product = new Product(name, price, purgomalumClient);
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final Product request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(
                        () -> new IllegalArgumentException("상품이 존재하지 않습니다 : " + productId)
                );
        productDomainService.changePrice(product, request.getPrice());
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
