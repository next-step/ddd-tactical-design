package kitchenpos.menus.tobe.domain.application;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import org.springframework.stereotype.Service;

@FunctionalInterface
public interface HideMenuWithInvalidPriceByProductId {
    void execute(UUID productId);
}

@Service
class DefaultHideMenuWithInvalidPriceByProductId implements HideMenuWithInvalidPriceByProductId {
    private final MenuRepository menuRepository;
    private final CalculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity calculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity;

    public DefaultHideMenuWithInvalidPriceByProductId(MenuRepository menuRepository,
                                                      CalculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity calculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity) {
        this.menuRepository = menuRepository;
        this.calculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity = calculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity;
    }

    @Override
    public final void execute(UUID productId) {
        final List<Menu> menus = menuRepository.findMenusByProductId(productId);
        for (final Menu menu : menus) {
            BigDecimal sum = calculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity.execute(menu);
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.hide();
            }
        }
    }
}
