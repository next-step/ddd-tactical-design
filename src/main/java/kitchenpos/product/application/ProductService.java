package kitchenpos.product.application;

import kitchenpos.menu.domain.service.MarginValidator;
import kitchenpos.product.domain.model.Product;
import kitchenpos.product.domain.model.ProductName;
import kitchenpos.product.domain.model.ProductNameCreationService;
import kitchenpos.product.domain.model.ProductPrice;
import kitchenpos.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductNameCreationService productNameCreationService;
    private final MarginValidator marginValidator;

    public ProductService(
            final ProductRepository productRepository,
            final ProductNameCreationService productNameCreationService,
            final MarginValidator marginValidator
    ) {
        this.productRepository = productRepository;
        this.productNameCreationService = productNameCreationService;
        this.marginValidator = marginValidator;
    }

    @Transactional
    public Product create(final Product request) {
        final BigDecimal price = request.getInnerPrice();
        final String name = request.getInnerName();
        final ProductName validProductName = productNameCreationService.createName(name);
        final Product product = new Product(validProductName, new ProductPrice(price));
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final UUID productId, final Product request) {
        final BigDecimal price = request.getInnerPrice();
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        product.changePrice(price);
        marginValidator.checkMargin(product);
        return product;
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
