package kitchenpos.menu.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.repository.MenuRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class DefaultMenuUpdatePolicy implements MenuUpdatePolicy {
    private final MenuRepository menuRepository;

    public DefaultMenuUpdatePolicy(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public void hideMenu(UUID productId) {
        List<Menu> menus = menuRepository.findAllByProductId(productId);

        menus.forEach(this::changeMenuDisplay);
    }

    private void changeMenuDisplay(Menu menu) {
        BigDecimal totalMenuProductPrice = calculateTotalMenuProductPrice(menu);

        boolean shouldBeDisplayed = menu.getPrice().compareTo(totalMenuProductPrice) <= 0;
        menu.setDisplayed(shouldBeDisplayed);
    }

    private BigDecimal calculateTotalMenuProductPrice(Menu menu) {
        return menu.getMenuProducts().stream()
            .map(menuProduct -> menuProduct.getProduct()
                .getPrice()
                .price()
                .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
            )
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
