package kitchenpos.apply.menus.tobe.domain;

import kitchenpos.apply.menus.tobe.ui.MenuRequest;
import kitchenpos.apply.products.tobe.domain.Product;
import kitchenpos.apply.products.tobe.domain.ProductRepository;
import kitchenpos.support.domain.Pair;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class MenuPriceCalculator {

    private final ProductRepository productRepository;

    public MenuPriceCalculator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public BigDecimal getTotalPriceFrom(final MenuRequest request) {
        return request.getMenuProductRequests().stream()
                .map(it -> new Pair<>(it.getProductId(), it.getQuantity()) )
                .map(it -> getProductTotalPrice(it.getKey(), it.getValue()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal getTotalPriceFrom(Menu menu) {
        return menu.getMenuProductList().stream()
                .map(it -> new Pair<>(it.getProductId(), it.getQuantity()) )
                .map(it -> getProductTotalPrice(UUID.fromString(it.getKey()), it.getValue()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal getProductTotalPrice(UUID productId, Long quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NoSuchElementException("Product not found for id: " + productId.toString()));
        return product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
}
