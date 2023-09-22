package kitchenpos.menu.adapter.menu.in;

import kitchenpos.menu.application.menu.port.in.MenuProductPriceUpdateUseCase;
import kitchenpos.product.adapter.out.ProductPriceChangeEvent;
import org.springframework.context.event.EventListener;

public class MenuProductPriceChangeEventListener {

    private final MenuProductPriceUpdateUseCase menuProductPriceUpdateUseCase;

    public MenuProductPriceChangeEventListener(
        final MenuProductPriceUpdateUseCase menuProductPriceUpdateUseCase) {

        this.menuProductPriceUpdateUseCase = menuProductPriceUpdateUseCase;
    }

    @EventListener
    public void listen(final ProductPriceChangeEvent event) {
        menuProductPriceUpdateUseCase.update(event.getId());
    }
}
