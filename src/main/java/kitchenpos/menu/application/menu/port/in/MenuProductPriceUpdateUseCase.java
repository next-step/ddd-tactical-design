package kitchenpos.menu.application.menu.port.in;

import java.util.UUID;
import kitchenpos.menu.application.exception.NotExistProductException;

public interface MenuProductPriceUpdateUseCase {

    /**
     * @throws NotExistProductException productId에 해당하는 음식이 없을 때
     */
    void update(final UUID productId);
}
