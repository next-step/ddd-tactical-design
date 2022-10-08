package kitchenpos.reader;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.exception.ProductNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ProductPriceReader {

    private final ProductRepository productRepository;

    public ProductPriceReader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public BigDecimal getProductPriceById(UUID productId) {
        Product product = findProductById(productId);
        return product.getPriceValue();
    }

    private Product findProductById(UUID productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("ID 에 해당하는 상품을 찾을 수 없습니다."));
    }
}
