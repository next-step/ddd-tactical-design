package kitchenpos.menus.tobe.domain.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import kitchenpos.products.tobe.domain.entity.Product;
import kitchenpos.products.tobe.domain.repository.ProductRepository;
import org.springframework.stereotype.Service;

@FunctionalInterface
public interface ProductPriceChangedEventHandler {
    void changeMenuProductPriceAndHide(UUID productId);
}

@Service
class DefaultProductPriceChangedEventHandler implements ProductPriceChangedEventHandler {
    private final MenuRepository menuRepository;
    private final ProductRepository productRepository;

    public DefaultProductPriceChangedEventHandler(MenuRepository menuRepository, ProductRepository productRepository) {
        this.menuRepository = menuRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void changeMenuProductPriceAndHide(UUID productId) {
        final List<Menu> menus = menuRepository.findMenusByProductId(productId);
        Product product = productRepository.findById(productId).get();
        for (final Menu menu : menus) {
            product.updateMenuProductPrice(menu);
            BigDecimal threshHoldPrice = menu.getSumOfProductPriceAndQuantity();
            if (menu.getPrice().compareTo(threshHoldPrice) > 0) {
                menu.hide();
            }
            menuRepository.saveMenu(menu);
        }
    }
}
