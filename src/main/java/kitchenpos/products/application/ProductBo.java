package kitchenpos.products.application;

import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class ProductBo {
    private final ProductRepository productRepository;

    public ProductBo(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(final Product product) {
        final BigDecimal price = product.getPrice();

        if (Objects.isNull(price) || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException();
        }

        return productRepository.save(product);
    }

    public List<Product> list() {

        return productRepository.findAll();
    }
}
