package kitchenpos.menu.adapter.menu.in;

import java.util.UUID;
import kitchenpos.menu.application.menu.port.in.MenuProductPriceUpdateUseCase;

public class MenuProductPriceChangeEventListener {

    private final MenuProductPriceUpdateUseCase menuProductPriceUpdateUseCase;

    public MenuProductPriceChangeEventListener(
        final MenuProductPriceUpdateUseCase menuProductPriceUpdateUseCase) {

        this.menuProductPriceUpdateUseCase = menuProductPriceUpdateUseCase;
    }

    public void listen(final UUID productId) {
        menuProductPriceUpdateUseCase.update(productId);
    }
}
