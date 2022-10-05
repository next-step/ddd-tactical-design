package kitchenpos.menus.menu.tobe.domain;

import kitchenpos.common.domain.vo.Price;
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
        for (final Menu menu : menus) {
            changeMenuProductPrice(menu, productId, price);
        }
    }

    private void changeMenuProductPrice(final Menu menu, final UUID productId, final Long price) {
        menu.changeMenuProductPrice(productId, Price.valueOf(price));
        if (menu.hasBiggerPriceThanTotalAmount()) {
            menu.hide();
        }
    }
}
