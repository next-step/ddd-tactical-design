package kitchenpos.products.tobe.infra;

import kitchenpos.menus.tobe.application.TobeMenuService;
import kitchenpos.products.tobe.application.ProductObserver;
import kitchenpos.products.tobe.domain.ChangePriceEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ChangePriceObserver implements ProductObserver {
    private TobeMenuService menuService;

    public ChangePriceObserver(TobeMenuService menuService) {
        this.menuService = menuService;
    }

    @EventListener
    @Override
    public void changeProductPrice(ChangePriceEvent event) {
        menuService.updateDisplayedStutus(event.getId());
    }
}
