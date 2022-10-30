package kitchenpos.products.application;

import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import kitchenpos.common.domain.vo.Price;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class ChangeProductPriceService {
    private final ProductRepository productRepository;

    public ChangeProductPriceService(
        final ProductRepository productRepository
    ) {
        this.productRepository = productRepository;
    }

    @Transactional
    public Product changePrice(final UUID productId, final BigDecimal price) {
        final Price productPrice = new Price(price);
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);

        product.changePrice(productPrice);

        productRepository.save(product);

        return product;
    }
}
