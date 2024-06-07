package kitchenpos.menus.tobe.domain.application;

import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.entity.MenuProduct;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface HideMenuWithInvalidPriceByProductId {
    void execute(UUID productId);
}

@Service
class DefaultHideMenuWithInvalidPriceByProductId implements HideMenuWithInvalidPriceByProductId {
    private final MenuRepository menuRepository;

    public DefaultHideMenuWithInvalidPriceByProductId(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public final void execute(UUID productId) {
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (final Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (final MenuProduct menuProduct : menu.getMenuProducts()) {
                sum = sum.add(
                        menuProduct.getProduct()
                                   .getPrice()
                                   .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
                );
            }
            if (menu.getPrice().compareTo(sum) > 0) {
                menu.setDisplayed(false);
            }
        }
    }
}
