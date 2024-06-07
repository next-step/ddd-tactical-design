package kitchenpos.menus.tobe.domain.application;

import kitchenpos.menus.tobe.domain.entity.Menu;
import kitchenpos.menus.tobe.domain.entity.MenuProduct;
import kitchenpos.menus.tobe.domain.repository.MenuRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.UUID;

public interface DisplayMenu {
    Menu execute(UUID menuId);
}

@Service
class DefaultDisplayMenu implements DisplayMenu {
    private final MenuRepository menuRepository;

    public DefaultDisplayMenu(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public final Menu execute(UUID menuId) {
        final Menu menu = menuRepository.findById(menuId)
                                        .orElseThrow(NoSuchElementException::new);
        BigDecimal sum = BigDecimal.ZERO;
        for (final MenuProduct menuProduct : menu.getMenuProducts()) {
            sum = sum.add(
                    menuProduct.getProduct()
                               .getPrice()
                               .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
            );
        }
        if (menu.getPrice().compareTo(sum) > 0) {
            throw new IllegalStateException();
        }
        menu.setDisplayed(true);
        return menu;
    }
}
