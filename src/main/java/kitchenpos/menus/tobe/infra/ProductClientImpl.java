package kitchenpos.menus.tobe.infra;

import kitchenpos.menus.tobe.domain.menu.ProductClient;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class ProductClientImpl implements ProductClient {
    private static final String NOT_FOUND_PRODUCT_EXCEPTION_MESSAGE = "조회하려고 하는 상품을 찾을 수 없습니다. productId : ";
    private final ProductRepository productRepository;

    public ProductClientImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public BigDecimal productPrice(UUID productId) {
        return productRepository.findById(productId)
                .map(Product::getPrice)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_PRODUCT_EXCEPTION_MESSAGE + productId));
    }

    @Override
    public int countMatchingProductIdIn(List<UUID> productIds) {
        return productRepository.findAllByIdIn(productIds).size();
    }
}
