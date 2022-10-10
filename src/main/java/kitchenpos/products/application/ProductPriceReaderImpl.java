package kitchenpos.products.application;

import java.math.BigDecimal;
import java.util.UUID;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.products.exception.ProductNotFoundException;
import kitchenpos.reader.ProductPriceReader;
import org.springframework.stereotype.Service;

@Service
public class ProductPriceReaderImpl implements ProductPriceReader {

    private final ProductRepository productRepository;

    public ProductPriceReaderImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public BigDecimal getProductPriceById(UUID productId) {
        Product product = findProductById(productId);
        return product.getPriceValue();
    }

    private Product findProductById(UUID productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new ProductNotFoundException("ID 에 해당하는 상품을 찾을 수 없습니다."));
    }
}
