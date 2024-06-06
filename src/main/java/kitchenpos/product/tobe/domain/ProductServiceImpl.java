package kitchenpos.product.tobe.domain;

import jakarta.transaction.Transactional;
import kitchenpos.annotation.DomainService;
import kitchenpos.menu.tobe.domain.Menu;
import kitchenpos.menu.tobe.domain.MenuRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@DomainService
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final MenuRepository menuRepository;

    public ProductServiceImpl(ProductRepository productRepository, MenuRepository menuRepository) {
        this.productRepository = productRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    @Transactional
    public void syncMenuDisplayStatisWithProductPrices(UUID productId, BigDecimal newPrice) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(NoSuchElementException::new);

        product.updateProductPrice(newPrice);

        final List<Menu> menus = menuRepository.findAllByProductId(product.getId());
        for (final Menu menu : menus) {
            if (menu.isMenuPriceHigherThanTotalPrice()) {
                menu.changeMenuDisplayStatus(false);
            }
        }
    }
}
