package kitchenpos.product.adapter.out;

import static kitchenpos.support.ParameterValidateUtils.checkNotNull;

import java.util.UUID;
import kitchenpos.menu.adapter.menu.in.MenuProductPriceChangeEventListener;
import kitchenpos.product.application.port.out.ProductPriceChangeEventPublisher;
import kitchenpos.product.support.constant.Name;

public class DefaultProductPriceChangeEventPublisher implements ProductPriceChangeEventPublisher {

    private final MenuProductPriceChangeEventListener menuProductPriceChangeEvent;

    public DefaultProductPriceChangeEventPublisher(
        final MenuProductPriceChangeEventListener menuProductPriceChangeEvent) {

        this.menuProductPriceChangeEvent = menuProductPriceChangeEvent;
    }

    @Override
    public void publish(final UUID id) {
        checkNotNull(id, Name.ID);

        menuProductPriceChangeEvent.listen(id);
    }
}
