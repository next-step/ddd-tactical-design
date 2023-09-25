package kitchenpos.menus.infra;

import kitchenpos.common.domain.vo.Price;
import kitchenpos.menus.domain.menu.MenuProduct;
import kitchenpos.menus.domain.menu.ProductClient;
import kitchenpos.products.domain.Product;
import kitchenpos.products.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class DefaultProductClient implements ProductClient {
    private final ProductRepository productRepository;

    public DefaultProductClient(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Price getProductPrice(final UUID productId) {
        return productRepository.findById(productId)
                .map(Product::getPrice)
                .map(Price::of)
                .orElse(null);
    }

    @Override
    public boolean isInvalidMenuProductsCount(final List<MenuProduct> menuProducts) {
        final List<UUID> productIds = menuProducts.stream()
                .map(MenuProduct::getProductId)
                .collect(Collectors.toList());
        final List<Product> products = productRepository.findAllByIdIn(productIds);
        return products.size() != menuProducts.size();
    }

}
