package kitchenpos.products.tobe.infra;

import kitchenpos.menus.tobe.application.TobeMenuService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InfraProductTranslator implements ProductTranslator {
    private final TobeMenuService menuService;

    public InfraProductTranslator(TobeMenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public void menuUpdateDisplayedStatus(UUID productId) {
        menuService.updateDisplayedStutus(productId);
    }
}
