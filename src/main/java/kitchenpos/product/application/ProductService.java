package kitchenpos.product.application;

import kitchenpos.infra.PurgomalumClient;
import kitchenpos.product.tobe.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final PurgomalumClient purgomalumClient;
    private final ProductDomainService productDomainService;

    public ProductService(
            final ProductRepository productRepository,
            final PurgomalumClient purgomalumClient,
            ProductDomainService productDomainServiceImpl) {
        this.productRepository = productRepository;
        this.purgomalumClient = purgomalumClient;
        this.productDomainService = productDomainServiceImpl;
    }

    @Transactional
    public Product create(final Product request) {
        final String name = request.getProductName();
        final BigDecimal price = request.getProductPrice();
        final var productName = new ProductName(name, purgomalumClient);
        final var productPrice = new ProductPrice(price);

        final Product product = new Product(productName, productPrice);
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final Product request) {
        final BigDecimal newPrice = request.getProductPrice();

        return productDomainService.syncMenuDisplayStatusWithProductPrices(productId, newPrice);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
