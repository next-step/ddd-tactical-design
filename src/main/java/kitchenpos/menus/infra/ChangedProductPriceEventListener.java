package kitchenpos.menus.infra;

import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import kitchenpos.menus.domain.Menu;
import kitchenpos.menus.domain.MenuDisplayPolicy;
import kitchenpos.menus.domain.MenuRepository;
import kitchenpos.menus.domain.Price;
import kitchenpos.products.domain.ChangedProductPriceEvent;

@Component
public class ChangedProductPriceEventListener {
    private final MenuRepository menuRepository;
    private final MenuDisplayPolicy menuDisplayPolicy;

    public ChangedProductPriceEventListener(MenuRepository menuRepository, MenuDisplayPolicy menuDisplayPolicy) {
        this.menuRepository = menuRepository;
        this.menuDisplayPolicy = menuDisplayPolicy;
    }

    @EventListener
    public void changeProductPrice(ChangedProductPriceEvent event) {
        List<Menu> menus = menuRepository.findAllByProductId(event.getProductId());
        for (Menu menu : menus) {
            menu.changeProductPrice(event.getProductId(), new Price(event.getPrice()), menuDisplayPolicy);
        }
    }
}
