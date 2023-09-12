package kitchenpos.menus.tobe.domain;

import kitchenpos.products.tobe.domain.ChangeProductPriceEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ChangeProductPriceEventHandler {

    private MenuRepository menuRepository;
    private ProductTotalPricePolicy productTotalPricePolicy;

    public ChangeProductPriceEventHandler(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @EventListener
    @Transactional
    public void handle(ChangeProductPriceEvent event) {
        final List<Menu> menus = menuRepository.findAllByProductId(event.getProductId());
        for (final Menu menu : menus) {
            menu.overMenuProductsTotalPriceThenHide(productTotalPricePolicy);
        }
    }

}
