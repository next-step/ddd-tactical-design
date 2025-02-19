package kitchenpos.menu.domain.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import kitchenpos.menu.domain.entity.Menu;
import kitchenpos.menu.domain.repository.InMemoryMenuRepository;
import kitchenpos.menu.domain.repository.MenuRepository;

public class FakeMenuUpdatePolicy implements MenuUpdatePolicy {
    private MenuRepository menuRepository = new InMemoryMenuRepository();

    public FakeMenuUpdatePolicy(MenuRepository menuRepository) {
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
                        .multiply(BigDecimal.valueOf(menuProduct.getQuantity()))
                );
            }
            menu.setDisplayed(menu.getPrice().compareTo(sum) <= 0);
        }
    }
}
