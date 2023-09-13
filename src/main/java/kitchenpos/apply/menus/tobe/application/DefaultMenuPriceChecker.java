package kitchenpos.apply.menus.tobe.application;

import kitchenpos.apply.menus.tobe.domain.MenuPriceChecker;
import kitchenpos.apply.menus.tobe.domain.MenuProduct;
import kitchenpos.apply.menus.tobe.domain.MenuRepository;
import kitchenpos.apply.products.tobe.domain.Product;
import kitchenpos.apply.products.tobe.domain.ProductRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Component
public class DefaultMenuPriceChecker implements MenuPriceChecker {

    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;

    public DefaultMenuPriceChecker(ProductRepository productRepository, MenuRepository menuRepository) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
    }

    public boolean isTotalPriceLowerThanMenu(BigDecimal menuPrice, List<MenuProduct> menuProducts) {
        BigDecimal totalPrice = menuProducts.stream()
                .map(it -> {
                    Product product = productRepository.findById(UUID.fromString(it.getProductId()))
                            .orElseThrow(() -> new NoSuchElementException("Product not found for id: " + it.getProductId()));
                    return product.getPrice().multiply(BigDecimal.valueOf(it.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return totalPrice.compareTo(menuPrice) < 0;
    }

    @Override
    public void checkMenuPriceAndHideMenuIfTotalPriceLower(UUID productId) {
        menuRepository.findAllByProductId(productId)
                .forEach(it -> it.hideIfMenuPriceTooHigher(this));
    }
}
