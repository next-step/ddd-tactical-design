package kitchenpos.menus.tobe.domain.application;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.UUID;
import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import org.springframework.stereotype.Service;

@FunctionalInterface
public interface DisplayMenu {
    Menu execute(UUID menuId);
}

@Service
class DefaultDisplayMenu implements DisplayMenu {
    private final MenuRepository menuRepository;
    private final CalculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity calculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity;

    public DefaultDisplayMenu(MenuRepository menuRepository,
                              CalculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity calculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity) {
        this.menuRepository = menuRepository;
        this.calculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity = calculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity;
    }

    @Override
    public final Menu execute(UUID menuId) {
        final Menu menu = menuRepository.findMenuById(menuId)
                                        .orElseThrow(NoSuchElementException::new);
        BigDecimal sum = calculateSumOfMultiplyingMenuProductPriceAndMenuProductQuantity.execute(menu);
        menu.displayOn(sum);
        return menu;
    }
}
