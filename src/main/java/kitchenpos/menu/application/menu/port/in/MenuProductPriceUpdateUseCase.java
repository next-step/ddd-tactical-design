package kitchenpos.menu.application.menu.port.in;

import java.util.UUID;

public interface MenuProductPriceUpdateUseCase {

    void update(final UUID productId);
}
