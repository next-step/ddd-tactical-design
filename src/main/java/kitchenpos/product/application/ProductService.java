package kitchenpos.product.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.common.domain.MarginValidator;
import kitchenpos.common.domain.Name;
import kitchenpos.common.domain.NameCreationService;
import kitchenpos.common.domain.Price;
import kitchenpos.product.domain.model.Product;
import kitchenpos.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final NameCreationService nameCreationService;
    private final MarginValidator marginValidator;

    public ProductService(
            final ProductRepository productRepository,
            final NameCreationService nameCreationService,
            final MarginValidator marginValidator
    ) {
        this.productRepository = productRepository;
        this.nameCreationService = nameCreationService;
        this.marginValidator = marginValidator;
    }

    @Transactional
    public Product create(final Product request) {
        final BigDecimal price = request.getInnerPrice();
        final String name = request.getInnerName();
        final Name validName = nameCreationService.createName(name);
        final Product product = new Product(validName, new Price(price), UUID.randomUUID());
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
