package kitchenpos.products.tobe.domain;

import jakarta.transaction.Transactional;
import kitchenpos.annotation.DomainService;
import kitchenpos.menus.tobe.domain.menu.Menu;
import kitchenpos.menus.tobe.domain.menu.MenuRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@DomainService
public class ProductPriceServiceImpl implements ProductPriceService {
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;

    public ProductPriceServiceImpl(ProductRepository productRepository, MenuRepository menuRepository) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    @Transactional
    public Product syncMenuDisplayStatusWithProductPrices(UUID productId, BigDecimal newPrice) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);

        product.updateProductPrice(newPrice);

        final List<Menu> menus = menuRepository.findAllByProductId(product.getId());
        for (final Menu menu : menus) {
            if (menu.isMenuPriceHigherThanTotalPrice()) {
                menu.setUndisplayed();
            }
        }
        return product;
    }
}
