package kitchenpos.menus.application;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import kitchenpos.reader.ProductPriceReader;

public class FakeProductPriceReader implements ProductPriceReader {

    private final ProductRepository productRepository;

    public FakeProductPriceReader(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public BigDecimal getProductPriceById(UUID productId) {
        Product product = findProductById(productId);
        return product.getPriceValue();
    }

    private Product findProductById(UUID productId) {
        return productRepository.findById(productId)
            .orElseThrow(() -> new NoSuchElementException("ID 에 해당하는 상품을 찾을 수 없습니다."));
    }
}
