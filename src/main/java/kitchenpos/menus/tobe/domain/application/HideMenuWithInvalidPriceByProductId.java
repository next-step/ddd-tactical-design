package kitchenpos.menus.tobe.domain.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

@FunctionalInterface
public interface HideMenuWithInvalidPriceByProductId {
    void execute(UUID productId);
}

@Service
class DefaultHideMenuWithInvalidPriceByProductId implements HideMenuWithInvalidPriceByProductId {
    private final MenuRepository menuRepository;
    private final ProductRepository productRepository;

    public DefaultHideMenuWithInvalidPriceByProductId(MenuRepository menuRepository,
                                                      ProductRepository productRepository) {
        this.menuRepository = menuRepository;
        this.productRepository = productRepository;
    }

    @Override
    public final void execute(UUID productId) {
        final List<Menu> menus = menuRepository.findMenusByProductId(productId);
        for (final Menu menu : menus) {
            BigDecimal threshHoldPrice = menu.getSumOfProductPriceAndQuantity(productRepository);
            if (menu.getPrice().compareTo(threshHoldPrice) > 0) {
                menu.hide();
            }
        }
    }
}
