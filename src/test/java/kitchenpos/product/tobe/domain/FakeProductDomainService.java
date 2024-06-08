package kitchenpos.product.tobe.domain;

import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;
import kitchenpos.products.tobe.domain.Product;
import kitchenpos.products.tobe.domain.ProductPriceService;
import kitchenpos.products.tobe.domain.ProductRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

public class FakeProductDomainService implements ProductPriceService {
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;

    public FakeProductDomainService(ProductRepository productRepository, MenuRepository menuRepository) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    public Product syncMenuDisplayStatusWithProductPrices(UUID productId, BigDecimal newPrice) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);

        product.updateProductPrice(newPrice);
        productRepository.save(product);

        final List<Menu> menus = menuRepository.findAllByProductId(product.getId());
        for (final Menu menu : menus) {
            if (menu.isMenuPriceHigherThanTotalPrice()) {
                menu.setUndisplayed();
            }
        }
        return product;
    }
}
