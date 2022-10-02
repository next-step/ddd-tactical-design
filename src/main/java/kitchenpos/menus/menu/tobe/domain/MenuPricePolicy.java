package kitchenpos.menus.menu.tobe.domain;

import kitchenpos.menus.menu.tobe.domain.vo.Price;
import kitchenpos.products.tobe.domain.PricePolicy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class MenuPricePolicy implements PricePolicy {

    private final MenuRepository menuRepository;

    public MenuPricePolicy(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public void changedProductPrice(final UUID productId, final Long price) {
        final List<Menu> menus = menuRepository.findAllByProductId(productId);
        for (Menu menu : menus) {
            menu.changeMenuProductPrice(productId, Price.valueOf(price));
        }
    }
}
