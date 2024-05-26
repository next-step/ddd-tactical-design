package kitchenpos.products.application;

import kitchenpos.products.application.dto.ProductPriceChangeCommand;
import kitchenpos.products.application.dto.ProductRegisterCommand;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.tobe.domain.ProductProfanityValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductProfanityValidator productProfanityValidator;

    public ProductService(
        final ProductRepository productRepository,
        final ProductProfanityValidator productProfanityValidator
    ) {
        this.productRepository = productRepository;
        this.productProfanityValidator = productProfanityValidator;
    }

    @Transactional
    public Product create(final ProductRegisterCommand command) {
        final Product product = new Product(productProfanityValidator, command.getName(), command.getPrice());
        return productRepository.save(product);
    }

    @Transactional
    public Product changePrice(final ProductPriceChangeCommand command) {
        final Product product = productRepository.findById(command.getId())
            .orElseThrow(NoSuchElementException::new);
        product.changePrice(command.getPrice());
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
