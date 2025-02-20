package kitchenpos.menu.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.repository.MenuRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DefaultMenuUpdatePolicy implements MenuUpdatePolicy{
    private final MenuRepository menuRepository;

    public DefaultMenuUpdatePolicy(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public void hideMenu(UUID productId) {
        List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (Menu menu : menus) {
            BigDecimal sum = BigDecimal.ZERO;
            for (var menuProduct : menu.getMenuProducts()) {
                sum = sum.add(
                    menuProduct.getProduct()
                        .getPrice()
                        .price()
                        .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
                );
            }
            menu.setDisplayed(menu.getPrice().compareTo(sum) <= 0);
        }
    }
}
