package kitchenpos.product.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menu.domain.service.MarginValidator;
import kitchenpos.product.application.dto.ChangeProductPriceServiceRq;
import kitchenpos.product.application.dto.CreateProductServiceRq;
import kitchenpos.product.application.dto.ProductServiceRs;
import kitchenpos.product.domain.model.Product;
import kitchenpos.product.domain.model.ProductName;
import kitchenpos.product.domain.model.ProductNameCreationService;
import kitchenpos.product.domain.model.ProductPrice;
import kitchenpos.product.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public ProductServiceRs create(final CreateProductServiceRq request) {
        final BigDecimal price = request.getPrice();
        final String name = request.getName();
        final ProductName validProductName = productNameCreationService.createName(name);
        final Product product = new Product(validProductName, new ProductPrice(price));
        productRepository.save(product);
        return new ProductServiceRs(product);
    }

    @Transactional
    public ProductServiceRs changePrice(final UUID productId, final ChangeProductPriceServiceRq request) {
        final BigDecimal price = request.getPrice();
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);
        product.changePrice(price);
        marginValidator.checkMargin(product);
        return new ProductServiceRs(product);
    }

    @Transactional(readOnly = true)
    public List<ProductServiceRs> findAll() {
        return productRepository.findAll().stream()
                .map(ProductServiceRs::new)
                .toList();
    }
}
